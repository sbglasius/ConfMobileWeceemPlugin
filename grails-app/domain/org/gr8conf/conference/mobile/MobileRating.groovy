package org.gr8conf.conference.mobile

class MobileRating {
    MobileRegistration user
    String uri
    int rating
    String comment

    static constraints = {
        user(nullable: false)
        uri(blank: false)
        rating(range: 1..5)
        comment(blank: true, maxSize: 2048)
    }
}
