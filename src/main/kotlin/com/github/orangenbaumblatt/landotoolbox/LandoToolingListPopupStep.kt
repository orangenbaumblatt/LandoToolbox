package com.github.orangenbaumblatt.landotoolbox

import com.intellij.openapi.ui.popup.ListSeparator
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep

class LandoToolingListPopupStep(
        title: String,
        items: List<LandoToolingItem>
) : BaseListPopupStep<LandoToolingItem>(title, items) {
    override fun onChosen(selectedValue: LandoToolingItem, finalChoice: Boolean): PopupStep<*>? {
        selectedValue.onChosen()
        return super.onChosen(selectedValue, finalChoice)
    }

    override fun getTextFor(value: LandoToolingItem): String {
        return value.getText()
    }

    override fun getSeparatorAbove(value: LandoToolingItem): ListSeparator? {
        return ListSeparator(value.getSeparatorText())
    }
}
