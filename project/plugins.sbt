resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// resolvers += ("Play2war plugins release" at "http://repository-play-war.forge.cloudbees.com/release/").withAllowInsecureProtocol(true)

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.20")



// Web plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.6")
addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.10")
addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.4")
addSbtPlugin("com.typesafe.sbt" % "sbt-mocha" % "1.1.2")
addSbtPlugin("org.irundaia.sbt" % "sbt-sassify" % "1.4.13")

// Play enhancer - this automatically generates getters/setters for public fields
// and rewrites accessors of these fields to use the getters/setters. Remove this
// plugin if you prefer not to have this feature, or disable on a per project
// basis using disablePlugins(PlayEnhancer) in your build.sbt
addSbtPlugin("com.typesafe.sbt" % "sbt-play-enhancer" % "1.2.2")

// Play Ebean support, to enable, uncomment this line, and enable in your build.sbt using
// enablePlugins(PlayEbean).
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "6.0.0")

// addSbtPlugin("com.github.play2war" % "play2-war-plugin" % "1.4-beta1")

// addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")

libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
