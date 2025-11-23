package omkar.android.projects.app.constants

class Constants {

    object Routes {
        const val HOME = "home"
        const val PROFILE = "profile"
        const val JOG_DETAILS_NO_ARG = "jog_details"
        const val JOG_DETAILS_WITH_ARG = "jog_details/{id}"
    }

    object Screen {
        const val HOME_PAGE = "JogIt"
        const val PROFILE_PAGE = "Profile"
    }

    object RemoteConstants {
        const val EXAMPLE_ENDPOINT = "example_endpoint"
    }

    object DataBaseConstants {
        const val DB_NAME = "jogit.db"
    }

    object PasswordConstants {
        val hexChars = "0123456789abcdef".toCharArray()
    }
}