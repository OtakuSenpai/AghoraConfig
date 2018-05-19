package com.otakusenpai.xmltest

fun main(args: Array<String>) {
    var config: ConfigHandler = ConfigHandler("./build/xml/test.xml")

    config.setChannelAttributes(false,"llamas")
    println()

    var foo = config.getPositiveChannelList()
    for(i in foo) println("${i}")
    println()

    var baz = config.getChildCommands()
    for(i in baz) println("${i}")
    println()

    var master = config.getMasterCommand()
    println("${master}")

    var desc = config.getDescription()
    println("${desc}")

    var name = config.getName()
    println("${name}")

    var allowed = config.ifMasksNotAllowed("user@host")
    if(!allowed)  println("works")

    config.setMasksAttributes("nick!user@host")
}