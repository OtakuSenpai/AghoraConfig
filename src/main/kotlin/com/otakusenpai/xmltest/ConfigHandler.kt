package com.otakusenpai.xmltest

import nu.xom.*
import java.io.File
import java.io.FileOutputStream

class ConfigHandler {

    constructor(path: String) {
        pathToXml = path
        var xmlBuilder = Builder()
        var f = File(path)
        xmlDocument = xmlBuilder.build(f)
        rootElement = xmlDocument.rootElement
    }

    fun getName(): String? {
        lateinit var name: String
        try {
            var name1 = rootElement.getFirstChildElement("name")
            name = name1.value
        } catch(e: Throwable) {
            e.printStackTrace()
        } catch(e: ParsingException) {
            e.printStackTrace()
        }
        return name
    }

    fun getDescription(): String? {
        lateinit var desc: String
        try {
            var desc1 = rootElement.getFirstChildElement("description")
            desc = desc1.value
        } catch(e: Throwable) {
            e.printStackTrace()
        } catch(e: ParsingException) {
            e.printStackTrace()
        }
        return desc
    }

    fun getMasterCommand(): String? {
        lateinit var msc: String
        try {
            var msc1 = rootElement.getFirstChildElement("MasterCommand")
            msc = msc1.value
        } catch(e: Throwable) {
            e.printStackTrace()
        } catch(e: ParsingException) {
            e.printStackTrace()
        }
        return msc
    }

    fun getChildCommands(): MutableList<String> {
        var childList: MutableList<String> = mutableListOf()

        try {
            var childrenList = rootElement.
                    getFirstChildElement("ChildCommands").childElements
            for(i in 0..(childrenList.size()-1)) {
                childList.add(childrenList.get(i).value)
            }
        } catch(e: Throwable) {
            e.printStackTrace()
        } catch(e: ParsingException) {
            e.printStackTrace()
        }
        return childList
    }

    fun getPositiveChannelList(): MutableList<String> {
        var channelList: MutableList<String> = mutableListOf()

        try {
            var childrenList =
                   rootElement.getFirstChildElement("channels").childElements
            for(i in 0..(childrenList.size()-1)) {
                if(childrenList.get(i).value == "yes")
                    channelList.add(childrenList.get(i).getAttribute("name").value)
            }
        } catch(e: Throwable) {
            e.printStackTrace()
        } catch(e: ParsingException) {
            e.printStackTrace()
        }
        return channelList
    }

    // True = yes
    // False = no

    fun setChannelAttributes(condition: Boolean,chan: String) {
        try {
            var childrenList = rootElement.getFirstChildElement("channels")
                    .childElements
            for(i in 0..(childrenList.size()-1)) {
                println("${childrenList.get(i).getAttribute("name").value} has value ${childrenList.get(i).value}")
                if (childrenList.get(i).getAttribute("name").value == chan) {
                    childrenList.get(i).removeChild(i)
                    if (condition)
                        childrenList.get(i).insertChild("yes", i)
                    else
                        childrenList.get(i).insertChild("no", i)
                }
            }
            writeDoc(xmlDocument)
        } catch(e: Throwable) {
            e.printStackTrace()
        } catch(e: ParsingException) {
            e.printStackTrace()
        }
    }

    fun ifMasksNotAllowed(prefix: String): Boolean {
        var found = false
        try {
            var childrenList = rootElement.getChildElements("masks")
            var size = childrenList.size()
            for(i in 0..size-1) {
                if(childrenList.get(i).value == prefix)
                    found = true
            }
        } catch(e: Throwable) {
            e.printStackTrace()
        } catch(e: ParsingException) {
            e.printStackTrace()
        }
        return found
    }

    fun setMasksAttributes(prefix: String) {
        try {
            var childrenList = rootElement.getFirstChildElement("masks")

            var newMask = Element("nick")
            newMask.appendChild(prefix)
            childrenList.appendChild(newMask)
            writeDoc(xmlDocument)
        } catch(e: Throwable) {
            e.printStackTrace()
        } catch(e: ParsingException) {
            e.printStackTrace()
        }
    }

    fun writeDoc(doc: Document) {
        try {
            var f = FileOutputStream(pathToXml)
            var serializer = Serializer(f,"UTF-8")
            serializer.write(xmlDocument)
        } catch(e: Throwable) {
            e.printStackTrace()
        }
    }

    var xmlDocument: Document
    var rootElement: Element
    var pathToXml: String = ""
}