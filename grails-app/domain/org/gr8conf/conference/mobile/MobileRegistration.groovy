package org.gr8conf.conference.mobile

import org.weceem.content.WcmSpace

class MobileRegistration {
    WcmSpace space
    String id
    String email
    String code
    boolean confirmed

    static constraints = {
        space nullable: false
        email email: true, blank: false
        code nullable: true, blank: true
    }

    static mapping = {
        id generator: "uuid"
    }

    def beforeInsert() {
        if(!code) {
            def random = new Random().nextInt(10000)
            this.code = random.toString().padLeft(4, '0')
        }
    }
}
