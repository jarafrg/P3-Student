plugins {
    alias(libs.plugins.algomate)
    alias(libs.plugins.jagr)
}

exercise {
    assignmentId.set("p3")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    studentId = "!!!!!ATTENTION-TU_ID NOT MATRIKEL Number"
    firstName = "!!!!!ATTENTION FIRST NAME"
    lastName = "!!!!!ATTENTION LAST NAME"
    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
    // Optionally require public grader for mainBuildSubmission task. Default is false
    requireGraderPublic = false
}

dependencies {
    testImplementation("org.junit-pioneer:junit-pioneer:2.0.0")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
}


