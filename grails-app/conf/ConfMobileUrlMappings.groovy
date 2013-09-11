import org.codehaus.groovy.grails.commons.ApplicationHolder

class ConfMobileUrlMappings {

    static mappings = { appContext ->
        "/crossdomain.xml"(controller:  'mobileSchedule', action: 'crossdomain')
        def ctx
        // Look for Grails 2.0 context arg
        if (appContext) {
            ctx = appContext
        } else {
            // Static holders are our only choice pre-2.0
            //noinspection GrDeprecatedAPIUsage
            ctx = ApplicationHolder.application.mainContext
        }

        def config = ctx.grailsApplication.config

        final MOBILE_PREFIX = (config.weceem.mobile.prefix instanceof String) ?
            config.weceem.mobile.prefix : 'mobile'

        println("MOBILE_PREFIX: $MOBILE_PREFIX")
        def mobileFunctionsPrefix = (MOBILE_PREFIX ? '/' : '') + "${MOBILE_PREFIX}"

        name data: delegate.(mobileFunctionsPrefix + "/$space?/data")(controller: "mobileStorage", parseRequest: true) {
            action = [GET: 'getData', POST: 'setData', PUT: 'setData']
        }

        delegate.(mobileFunctionsPrefix + "/$space?/reg/$action") {
            controller = "mobileStorage"
        }

        name app: delegate.(mobileFunctionsPrefix + "/$space?/$uri**") {
            controller = "mobileSchedule"
            action = "getJson"
        }

    }
}
