/*
 * Copyright © 2021 Pavel Kakolin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

val nativeLibs = listOf(
    "http://ftp.debian.org/debian/pool/main/libg/libgpiod/libgpiod2_1.6.2-1_armhf.deb",
    "http://ftp.debian.org/debian/pool/main/libg/libgpiod/libgpiod2_1.6.2-1_arm64.deb",
    "http://ftp.debian.org/debian/pool/main/libg/libgpiod/libgpiod-dev_1.6.2-1_armhf.deb",
    "http://ftp.debian.org/debian/pool/main/libg/libgpiod/libgpiod-dev_1.6.2-1_arm64.deb",
    "http://ftp.debian.org/debian/pool/main/i/i2c-tools/libi2c-dev_4.2-1+b1_armhf.deb",
    "http://ftp.debian.org/debian/pool/main/i/i2c-tools/libi2c-dev_4.2-1+b1_arm64.deb",
    "http://ftp.debian.org/debian/pool/main/i/i2c-tools/libi2c0_4.2-1+b1_armhf.deb",
    "http://ftp.debian.org/debian/pool/main/i/i2c-tools/libi2c0_4.2-1+b1_arm64.deb"
)

val subtasks = nativeLibs.mapIndexed { index, url ->
    val download = tasks.register("download$index") {
        val target = file("$buildDir/native/$index")
        downloadFile(url, target)
        outputs.file(target)
    }

    val unpackData = tasks.register<Exec>("unpackData$index") {
        val target = file("$buildDir/native/unpacked/$index/data.tar.xz")
        target.parentFile.mkdirs()
        workingDir(target.parentFile)
        inputs.file("$buildDir/native/$index")
        outputs.file(target)
        commandLine("ar", "x", "$buildDir/native/$index", "data.tar.xz")
        dependsOn(download)
    }

    tasks.register<Exec>("unpackLib$index") {
        val unpackDataTask = unpackData.get()
        val target = file("$buildDir/native/libs/")
        val source = unpackDataTask.outputs.files.singleFile
        target.mkdirs()
        workingDir("$buildDir/native/libs/")
        inputs.file(source)
        commandLine("tar", "-xf", source.absolutePath, "./usr/lib/")

        dependsOn(unpackData)
    }
}

val nativeLibsTask = tasks.register("nativeLibs") {
    dependsOn(subtasks)
}

fun downloadFile(from: String, to: File) {
    val url = uri(from).toURL()
    val conn = url.openConnection()
    conn.getInputStream().use { iis ->
        to.parentFile.mkdirs()
        to.outputStream().use { oos ->
            iis.copyTo(oos)
        }
    }
}