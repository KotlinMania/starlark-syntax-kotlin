// port-lint: source src/golden_test_template.rs
@file:OptIn(kotlin.wasm.ExperimentalWasmInterop::class)
package io.github.kotlinmania.starlarksyntax.goldentesttemplate

/*
 * Copyright 2018 The Starlark in Rust Authors.
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import kotlin.wasm.WasmImport
import kotlin.wasm.unsafe.MemoryAllocator
import kotlin.wasm.unsafe.Pointer
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

@WasmImport("wasi_snapshot_preview1", "path_open")
internal external fun path_open(
    fd: Int,
    dirflags: Int,
    pathPtr: Int,
    pathLen: Int,
    oflags: Int,
    fsRightsBase: Long,
    fsRightsInheriting: Long,
    fdFlags: Short,
    resultPtr: Int
): Int

@WasmImport("wasi_snapshot_preview1", "fd_read")
internal external fun fd_read(
    fd: Int,
    iovecsPtr: Int,
    iovecsLen: Int,
    resultPtr: Int
): Int

@WasmImport("wasi_snapshot_preview1", "fd_write")
internal external fun fd_write(
    fd: Int,
    iovecsPtr: Int,
    iovecsLen: Int,
    resultPtr: Int
): Int

@WasmImport("wasi_snapshot_preview1", "fd_close")
internal external fun fd_close(fd: Int): Int

@WasmImport("wasi_snapshot_preview1", "environ_sizes_get")
internal external fun environ_sizes_get(environCountPtr: Int, environBufSizePtr: Int): Int

@WasmImport("wasi_snapshot_preview1", "environ_get")
internal external fun environ_get(environPtr: Int, environBufPtr: Int): Int

@OptIn(UnsafeWasmMemoryApi::class)
private fun Pointer.loadInt(offset: Int): Int = (this + offset).loadInt()

@OptIn(UnsafeWasmMemoryApi::class)
private fun Pointer.loadByte(offset: Int): Byte = (this + offset).loadByte()

@OptIn(UnsafeWasmMemoryApi::class)
private fun Pointer.storeInt(offset: Int, value: Int) {
    (this + offset).storeInt(value)
}

@OptIn(UnsafeWasmMemoryApi::class)
private fun Pointer.storeByte(offset: Int, value: Byte) {
    (this + offset).storeByte(value)
}

@OptIn(UnsafeWasmMemoryApi::class)
private fun MemoryAllocator.allocateInt(): Pointer = allocate(4)

@OptIn(UnsafeWasmMemoryApi::class)
internal actual fun platformGetEnv(name: String): String? {
    if (name == "CARGO_MANIFEST_DIR") {
        return "."
    }
    return withScopedMemoryAllocator { allocator ->
        val countPtr = allocator.allocateInt()
        val sizePtr = allocator.allocateInt()
        val sizesRes = environ_sizes_get(countPtr.address.toInt(), sizePtr.address.toInt())
        if (sizesRes != 0) return@withScopedMemoryAllocator null

        val count = countPtr.loadInt()
        val size = sizePtr.loadInt()
        if (count == 0 || size == 0) return@withScopedMemoryAllocator null

        val ptrsBuffer = allocator.allocate(count * 4)
        val dataBuffer = allocator.allocate(size)

        val getRes = environ_get(ptrsBuffer.address.toInt(), dataBuffer.address.toInt())
        if (getRes != 0) return@withScopedMemoryAllocator null

        for (i in 0 until count) {
            val envPtr = ptrsBuffer.loadInt(i * 4)
            val offset = envPtr - dataBuffer.address.toInt()

            var len = 0
            while ((dataBuffer + (offset + len)).loadByte().toInt() != 0) {
                len++
            }

            val bytes = ByteArray(len)
            for (j in 0 until len) {
                bytes[j] = (dataBuffer + (offset + j)).loadByte()
            }
            val entry = bytes.decodeToString()
            val eq = entry.indexOf('=')
            if (eq != -1) {
                val key = entry.substring(0, eq)
                if (key == name) {
                    return@withScopedMemoryAllocator entry.substring(eq + 1)
                }
            }
        }
        null
    }
}

@OptIn(UnsafeWasmMemoryApi::class)
internal actual fun platformReadUtf8File(path: String): String {
    val relativePath = path.removePrefix("./")
    return withScopedMemoryAllocator { allocator ->
        val fdPtr = allocator.allocateInt()
        val pathBytes = relativePath.encodeToByteArray()
        val pathBuffer = allocator.allocate(pathBytes.size)
        for (i in pathBytes.indices) {
            (pathBuffer + i).storeByte(pathBytes[i])
        }

        val openRes = path_open(
            fd = 3,
            dirflags = 1,
            pathPtr = pathBuffer.address.toInt(),
            pathLen = pathBytes.size,
            oflags = 0,
            fsRightsBase = 2L,
            fsRightsInheriting = 0L,
            fdFlags = 0,
            resultPtr = fdPtr.address.toInt()
        )
        if (openRes != 0) {
            error("Failed to open file $path via WASI path_open, errno: $openRes")
        }
        val fileFd = fdPtr.loadInt()

        try {
            val readBuffer = allocator.allocate(8192)
            val iovec = allocator.allocate(8)
            iovec.storeInt(0, readBuffer.address.toInt())
            iovec.storeInt(4, 8192)

            val bytesReadPtr = allocator.allocateInt()
            val output = mutableListOf<Byte>()

            while (true) {
                val readRes = fd_read(
                    fd = fileFd,
                    iovecsPtr = iovec.address.toInt(),
                    iovecsLen = 1,
                    resultPtr = bytesReadPtr.address.toInt()
                )
                if (readRes != 0) {
                    error("Failed to read file $path via WASI fd_read, errno: $readRes")
                }
                val readBytes = bytesReadPtr.loadInt()
                if (readBytes == 0) break

                for (i in 0 until readBytes) {
                    output.add((readBuffer + i).loadByte())
                }
            }
            output.toByteArray().decodeToString()
        } finally {
            fd_close(fileFd)
        }
    }
}

@OptIn(UnsafeWasmMemoryApi::class)
internal actual fun platformWriteUtf8File(path: String, content: String) {
    val relativePath = path.removePrefix("./")
    withScopedMemoryAllocator { allocator ->
        val fdPtr = allocator.allocateInt()
        val pathBytes = relativePath.encodeToByteArray()
        val pathBuffer = allocator.allocate(pathBytes.size)
        for (i in pathBytes.indices) {
            (pathBuffer + i).storeByte(pathBytes[i])
        }

        val openRes = path_open(
            fd = 3,
            dirflags = 1,
            pathPtr = pathBuffer.address.toInt(),
            pathLen = pathBytes.size,
            oflags = 9,
            fsRightsBase = 192L,
            fsRightsInheriting = 0L,
            fdFlags = 0,
            resultPtr = fdPtr.address.toInt()
        )
        if (openRes != 0) {
            error("Failed to open file $path for writing via WASI path_open, errno: $openRes")
        }
        val fileFd = fdPtr.loadInt()

        try {
            val contentBytes = content.encodeToByteArray()
            val writeBuffer = allocator.allocate(contentBytes.size)
            for (i in contentBytes.indices) {
                (writeBuffer + i).storeByte(contentBytes[i])
            }

            val iovec = allocator.allocate(8)
            iovec.storeInt(0, writeBuffer.address.toInt())
            iovec.storeInt(4, contentBytes.size)

            val bytesWrittenPtr = allocator.allocateInt()
            val writeRes = fd_write(
                fd = fileFd,
                iovecsPtr = iovec.address.toInt(),
                iovecsLen = 1,
                resultPtr = bytesWrittenPtr.address.toInt()
            )
            if (writeRes != 0) {
                error("Failed to write to file $path via WASI fd_write, errno: $writeRes")
            }
        } finally {
            fd_close(fileFd)
        }
    }
}

internal actual fun platformIsWindows(): Boolean = false
