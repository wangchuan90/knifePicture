/*
 * Copyright 2017 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wang.knifepicture

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.ByteBuffer
import java.security.MessageDigest

/**
 * 加载图片合集中的区域图片
 * 图片合集thumbnailsUrl：https://bitdash-a.akamaihd.net/content/MI201109210084_1/thumbnails/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.jpg
 * 使用：
 * GlideApp.with(imageView)
 * .load(thumbnailsUrl)
 * .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
 * .transform(new GlideThumbnailTransformation(currentPosition))
 * .into(imageView);
 */
class GlideThumbnailTransformation(position: Long) : BitmapTransformation() {
    private val x: Int
    private val y: Int

    init {
        val square = position.toInt() / THUMBNAILS_EACH
        y = square / MAX_LINES
        x = square % MAX_COLUMNS
    }

    override fun transform(
        pool: BitmapPool, toTransform: Bitmap,
        outWidth: Int, outHeight: Int
    ): Bitmap {
        val width = toTransform.width / MAX_COLUMNS
        val height = toTransform.height / MAX_LINES
        return Bitmap.createBitmap(toTransform, x * width, y * height, width, height)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        val data = ByteBuffer.allocate(8).putInt(x).putInt(y).array()
        messageDigest.update(data)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as GlideThumbnailTransformation
        return if (x != that.x) false else y == that.y
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    companion object {
        private const val MAX_LINES = 7
        private const val MAX_COLUMNS = 7
        private const val THUMBNAILS_EACH = 5000 // milliseconds
    }
}