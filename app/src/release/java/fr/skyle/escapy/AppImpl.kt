package fr.skyle.escapy

import fr.skyle.escapy.log.TimberLogImplementation


class AppImpl : EscapyApplication() {

    override fun initTimber() {
        super.initTimber()
        TimberLogImplementation.init()
    }
}