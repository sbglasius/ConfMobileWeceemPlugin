package org.gr8conf.conference.mobile

import grails.validation.Validateable
import groovy.transform.ToString


@ToString(includeNames = true)
@Validateable
class MobileStorageCommand {
    String space
    String email
    String code
    List<Map> favorites = [].withEagerDefault { [:]}
    List<Map> ratings = [].withEagerDefault {  [:] }
}
