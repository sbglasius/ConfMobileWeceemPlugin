package org.gr8conf.conference.mobile

import grails.converters.JSON

class MobileStorageController {
    def mobileStorageService

    def register(String space, String email) {
        log.debug("Register user: $space, $email")

        LinkedHashMap result = mobileStorageService.registerUser(space, email)
        setResponseHeaders()
        render(result as JSON)
    }


    def validate(String space, String id, String code) {
        log.debug("Validating user $id")

        def user = mobileStorageService.validateUser(space, id, code)
        if (user) {
            user.confirmed = true
            flash.message = "Your email address was confirmed. Now go and use the mobile app!"
        } else {
            flash.message = "Your email address was not found. Please start over in the mobile app on your device!"
        }

        redirect(uri: wcm.createLink(space: space, path: "index"))
    }

    def getData(String space, String email, String code) {
        log.debug("get data $space, $email, $code")
        def data = mobileStorageService.getData(space, email, code)
        setResponseHeaders()
        render(data as JSON)
    }

    def setData(MobileStorageCommand cmd) {
        log.debug("set data $cmd")
        def result = mobileStorageService.setData(cmd)
        setResponseHeaders()
        render(result as JSON)
    }

    def void setResponseHeaders() {
//        response.setHeader('Access-Control-Allow-Origin', request.getHeader("Origin"))
//        response.setHeader('Access-Control-Allow-Methods', 'POST, PUT, GET, OPTIONS, PATCH')
//        response.setHeader('Access-Control-Allow-Credentials', 'true')
//        response.setHeader('Access-Control-Allow-Headers', 'origin, x-requested-with, accept, content-type')
//        response.setHeader('Access-Control-Request-Headers', request.getHeader('Access-Control-Request-Headers'));
    }

    def test() {

    }


}


