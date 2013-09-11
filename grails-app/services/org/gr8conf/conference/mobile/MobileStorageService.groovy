package org.gr8conf.conference.mobile

import grails.gsp.PageRenderer
import org.grails.mandrill.MandrillMessage
import org.grails.mandrill.MandrillRecipient

class MobileStorageService {
    def mandrillService
    def wcmContentRepositoryService
    PageRenderer groovyPageRenderer
    def messageSource

    def LinkedHashMap registerUser(String space, String email) {

        def wcmSpace = getSpace(space)
        MobileRegistration user = MobileRegistration.findBySpaceAndEmail(wcmSpace, email) ?: new MobileRegistration(space: wcmSpace, email: email)
        def result = [:]
        if (!user.validate()) {
            result.errors = user.errors.fieldErrors.collectEntries {
                [it.field, messageSource.getMessage(it.code, it.arguments, it.defaultMessage, Locale.US)]
            }
        } else {
            def message = new MandrillMessage(
                    subject: "GR8Conf mobile app",
                    from_email: "info@gr8conf.org",
                    from_name: "GR8Conf",
                    to: [new MandrillRecipient(name: email, email: email)]
            )
            user.save(flush: true)
            def whatTemplate = user.confirmed ? "reminder_mail" : "registration_mail"
            log.debug("WhatTemplate: $whatTemplate")
            def template = groovyPageRenderer.render(template: "/mobileStorage/$whatTemplate", plugin: 'conf-mobile-weceem', model: [userId: user.id, code: user.code, space: space])
            log.debug("Template: $template")
            log.debug mandrillService.sendTemplate(message, "GR8Conf Europe 2013 mandrill", [std_content: template]);
            result.confirmed = user.confirmed
        }
        result
    }

    def validateUser(String space, String id, String code) {
        def wcmSpace = getSpace(space)
        def user = MobileRegistration.where {
            space == wcmSpace
            id == id
            code == code
        }.get()
        if (user) {
            user.confirmed = true
        }
        return user

    }

    def getSpace(String space) {
        wcmContentRepositoryService.findSpaceByURI(space)
    }


    Map getData(String space, String email, String code) {
        def result = [space: space, email: email]
        MobileRegistration user = getUserBySpaceEmailAndCode(space, email, code)
        if (user) {
            result << [
                    status: 'known',
                    confirmed: user.confirmed
            ]
            if (user.confirmed) {
                result << createFavoritesAndRatings(user)
            }
        } else {
            result.status = 'unknown'
        }
        return result
    }

    private LinkedHashMap<String, Object> createFavoritesAndRatings(MobileRegistration user) {
        [
                code: user.code,
                favorites: MobileFavorite.findAllByUser(user).collect {
                    [
                            uri: it.uri
                    ]
                },
                ratings: MobileRating.findAllByUser(user).collect {
                    [
                            uri: it.uri,
                            rating: it.rating,
                            comment: it.comment
                    ]
                }
        ]
    }


    def setData(MobileStorageCommand data) {
        def result = [
                space: data.space,
                email: data.email
        ]
        MobileRegistration user = getUserBySpaceEmailAndCode(data.space, data.email, data.code)
        if (user) {
            result << [
                    status: 'known',
                    confirmed: user.confirmed
            ]
            handleMobileFavorites(user, data)
            handleMobileRatings(user, data)
            result << createFavoritesAndRatings(user)
        } else {
            result.status = 'unknown'
        }
        return result
    }

    private void handleMobileFavorites(MobileRegistration user, MobileStorageCommand data) {
        MobileFavorite.findAllByUser(user)*.delete()
        data.favorites.unique { it.uri }.each {
            log.debug("Create new favorite: $user $it")
            new MobileFavorite(user: user, uri: it.uri).save(failOnError: true)
        }
    }

    private void handleMobileRatings(MobileRegistration user, MobileStorageCommand data) {
        MobileRating.findAllByUser(user)*.delete()
        data.ratings.unique { it.uri }.each {
            log.debug("Create new rating: $user $it")
            new MobileRating(user: user, uri: it.uri, rating: it.rating, comment: it.comment).save(failOnError: true)
        }
    }


    private MobileRegistration getUserBySpaceEmailAndCode(String space, String email, String code) {
        def wcmSpace = getSpace(space)
        return MobileRegistration.where {
            space == wcmSpace
            email == email
            code == code
        }.get()
    }
}
