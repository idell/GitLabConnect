package com.github.idell.gitlabconnect.ui

import java.awt.Desktop
import java.awt.event.MouseEvent
import javax.swing.event.HyperlinkEvent
import javax.swing.event.HyperlinkListener

class BrowserHyperLinkListener(private val desktop: Desktop) : HyperlinkListener {
    override fun hyperlinkUpdate(e: HyperlinkEvent) {
        val id: Int? = e.inputEvent?.id
        if (MouseEvent.MOUSE_CLICKED == id) {
            desktop.browse(e.url?.toURI())
        }
    }

}