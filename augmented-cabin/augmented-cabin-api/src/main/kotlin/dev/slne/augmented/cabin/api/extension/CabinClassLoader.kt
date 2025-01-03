package dev.slne.augmented.cabin.api.extension

import java.net.URL
import java.net.URLClassLoader

class CabinClassLoader(toJoin: ClassLoader) : URLClassLoader(emptyArray(), toJoin) {
    public override fun addURL(url: URL?) {
        super.addURL(url)
    }
}