package org.gr8conf.conference.mobile

class MobileFavorite {
    MobileRegistration user
    String uri

    static constraints = {
        user(nullable: false)
        uri(blank: false)
    }
}
