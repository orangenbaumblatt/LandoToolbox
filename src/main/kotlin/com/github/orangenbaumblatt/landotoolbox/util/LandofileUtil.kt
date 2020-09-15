package com.github.orangenbaumblatt.landotoolbox.util

import com.intellij.openapi.vfs.VirtualFile


class LandofileUtil {
    companion object {
        fun isLandofile(file: VirtualFile): Boolean {
            return !file.isDirectory && file.name.matches("""\.lando(\.\w+)?.yml""".toRegex())
        }
    }
}
