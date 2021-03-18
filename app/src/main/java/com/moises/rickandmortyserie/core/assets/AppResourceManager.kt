package com.moises.rickandmortyserie.core.assets

import android.content.Context
import javax.inject.Inject


class AppResourceManager @Inject constructor(private val context: Context): ResourceManager {

    override fun providesStringMessage(identifier: String): String {
        return try {
            context.resources.getIdentifier(identifier, AssetType.STRING.type, context.packageName).let {
                context.getString(it)
            }
        } catch (e : Exception) {
            ""
        }
    }

    override fun providesStringResource(identifier: String): Int? {
        return try {
            context.resources.getIdentifier(identifier, AssetType.STRING.type, context.packageName)
        } catch (e : Exception) {
            null
        }
    }

    override fun providesDrawableResource(identifier: String): Int? {
        return try {
            context.resources.getIdentifier(identifier, AssetType.DRAWABLE.type, context.packageName)
        } catch (e : Exception) {
            null
        }
    }
}