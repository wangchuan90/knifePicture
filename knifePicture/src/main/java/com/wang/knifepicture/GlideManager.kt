package com.wang.knifepicture

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation

/**
 * @author wangchuan
 * @date 2018/11/30
 * glide工具类
 */

object GlideManager {
    val defautImage = GlideConfig.defaultImg
    /**
     * ---------------常规配置-----------------------
     */
    /**
     * 缓存配置
     */
    val cacheOptions: RequestOptions= GlideConfig.CacheOptions()

    /**
     * 加载圆形图片配置
     */
    val circleCropOptions: RequestOptions= GlideConfig.CircleCropOptions()

    /**
     * 图片请求加载添加头参数
     */
    fun addHeaderUrl(path: String?, key: String?, value: String?): GlideUrl? {
        return if (TextUtils.isEmpty(path)) {
            null
        } else GlideUrl(
            path, LazyHeaders.Builder()
                .addHeader(key!!, value!!)
                .build()
        )
    }

    /**
     * 清除缓存
     */
    fun clear(context: Context?) {
        Glide.get(context!!).clearDiskCache()
    }

    /**
     * 公共图片加载
     */
    fun commonLoadImg(options: RequestOptions?, model: Any?, imageView: ImageView?) {
        if (model == null || imageView == null) {
            return
        }
        val context = imageView.context
        if (context is Activity) {
            val activity = context
            if (activity.isFinishing || activity.isDestroyed) {
                return
            }
        }
        Glide.with(imageView).load(model).apply(options!!).into(imageView)
    }

    /**
     * 一般图片加载
     */
    fun loadImg(model: Any?, imageView: ImageView?) {
        commonLoadImg(cacheOptions, model, imageView)
    }

    fun loadImg(url: String?, imageView: ImageView?) {
        commonLoadImg(cacheOptions, url, imageView)
    }

    fun loadImg(width: Int, heigh: Int, model: Any?, imageView: ImageView?) {
        commonLoadImg(cacheOptions.override(width, heigh), model, imageView)
    }

    fun loadCenterCropImg(model: Any?, imageView: ImageView?) {
        commonLoadImg(cacheOptions.centerCrop(), model, imageView)
    }

    /**
     * 加载圆形图片
     */
    fun loadCircleCropImg(model: Any?, imageView: ImageView?) {
        commonLoadImg(circleCropOptions, model, imageView)
    }

    /**
     * 高斯模糊效果
     *
     * @param blurRadius：模糊度 （25f是最大模糊度）
     *@param sampling ：采样值（值越大，模糊度效果越好）
     */
    fun loadBlurImg(
        model: Any?,
        imageView: ImageView,
        blurRadius: Int,
        sampling: Int,
        @DrawableRes resourceId: Int
    ) {
        val multi: MultiTransformation<Bitmap?> =
            MultiTransformation<Bitmap?>( //                radius ：模糊度
                BlurTransformation(blurRadius, sampling),  //                颜色处理
                ColorFilterTransformation(Color.argb(66, 54, 54, 54))
            )
        val requestOptions = RequestOptions.bitmapTransform(multi).error(resourceId)
        Glide.with(imageView.context)
            .load(model)
            .apply(requestOptions) //淡入淡出
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    fun loadBlurImg(model: Any?, imageView: ImageView, radius: Int, @DrawableRes resourceId: Int) {
        loadBlurImg(model, imageView, radius, 10, resourceId)
    }

    fun loadBlurImg(model: Any?, imageView: ImageView, radius: Int) {
        loadBlurImg(model, imageView, radius, 10, defautImage)
    }

}