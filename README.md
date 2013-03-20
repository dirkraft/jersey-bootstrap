Jersey Bootstrap
================
also known as "Texas Bootstrap" and "Double Bootstrap Rainbow".

This project exists solely to be shamelessly cloned, forked, gutted, deleted, and so on because bootstrapping a Java
web application is of the smaller varieties of fun. *This repo itself is not a new framework*, though it is based on a lightly configured [Jersey](http://jersey.java.net/).

I apologize for using the F-word (f----work). It will not happen again.

* * * * * *





"Features"
----------
Here's what you get regardless of what you want.

# Stuff. #

But really, you got a bunch because the truth is
### It is easy and fun to delete stuff. ###
It is less fun to track down disparate dependencies. Once long ago I made Unreal Tournament levels and (at that young
age) was blown away by the seemingly upside-down concept of the level editor: you don't *add* objects to an empty
world; you carve out rooms (*subtract*) from an entirely solid world. After subtracting out space for the general flow
of the level, THEN you would add in the details, static meshes and whatnot.


#### You get the following server-side things ####

 - **Jetty server** - [(link)](http://jetty.codehaus.org/jetty/)
 - **Jersey** - [(link)](http://jersey.java.net/) set up to enable the following. It may be useful to examine `RunServer.run()` at how Jetty and Jersey are configured from the get-go.
    * Serves up **embedded or external static resources**. URLs match jersey resources (controllers, REST endpoints, ...) first. If there is not one, falls back to static resources.
      + more on `-Dbase.static_dirs` under [[As a developer...]](#as-a-developer)
    * Basic [**DI/IoC**](http://en.wikipedia.org/wiki/Inversion_of_control). All resources (classes annotated `@Provider`, `@Path`, others...) are singletons. DI via `@InjectParam` annotated arguments.
    * **Jackson json** serialization. Check out `DefaultObjectMapper` for Jackson config.
    * **UTF-8** everything. As long as you make sure all your `@Path` classes extend `BaseJsonResource`
 - **fat jar** build - gradle plugin to build an all-in-one runnable jar [(link)](https://github.com/musketyr/gradle-fatjar-plugin)
 - **JSP's disabled** - so you won't be tempted to use it or anything that depends on it, ever. If you want these, go away.


#### You get the following browser-side things ####

 - **bootstrap.css v2.2.2** - the one and only [Twitter Bootstrap](http://twitter.github.com/bootstrap/)
 - **font-awesome.css v3.0.2** - is awesome because the icons are just font characters, and so can be colored or displayed at any
                        size. [(link)](http://fortawesome.github.com/Font-Awesome/)
 - **jQuery v1.8.3** - If you don't know what this is... I don't even know what they would do to you. [Them.](http://cl.jroo.me/z3/U/C/k/e/a.baa-Them-Kittens.jpg)
 - **HTML5 Boilerplate v4.1.0** - All I did was dump the files in, but there you go.
   [HTML5 boilerplate](http://html5boilerplate.com/)

Refer to the [[File Matrix section]](#file-matrix) and each external source's site and documentation to act upon said
resources.





Bootstrappin'
-------------
Pretty much you'll want to do a full project text search for ∏ (that's capital π). Anywhere that character appears
is something that you'll want to change to Make It Yours (including this README.md)! These are the minimum required to erase all significant traces
that you stole this code.

 1. root directory - rename from 'jersey-bootstrap'
 2. `build.gradle` - group name
 3. delete the two `placeholder` files in src/test/java and src/test/resources
 4. package structure - move everything from 'com/github/dirkraft/jerseyboot' to 'com/mega/corp/imaedthismyslef'

Then you'll want to decide what you want to keep, lose, or modify from the various static resources sources, and make
plans to replace any such placeholders. The [[File Matrix section]](#file-matrix) may help with this. Refer to each's
own site and documentation for details.

Once that's all done, you'll want to quit IntelliJ and run yourself one final `gradle idea` before making Exodus of
this README.md (delete it or replace it with your own). Stealing code has never been so easy!





License
-------
Jersey Bootstrap is licensed under WTFPL v2. A copy is available in the project, [WTFPL.txt](WTFPL.txt). You should
DWTFYW with this file (like deleting it) if you don't want your derivative work to be subject to the same license.





As a developer...
-----------------
(I tire of the Agile methodology) I configure my IDE with useful settings.


### Running/Debugging the App ###

 1. determine the value for `-Dbase.static_dirs`
   * in a single web module project structure, just

            -Dbase.static_dirs=src/main/webapp/static/
   * in a multi-module web module project structure first-one-wins order of static directories, e.g.

            -Dbase.static_dirs=src/main/webapp/static/;../base-webapp/src/main/webapp/static/

 2. run or debug `RunServer.main` with that system property. `-Dbase.static_dirs` is normally only useful for development.

- - - - - - -

### Usage ###
If you want something serialized to and from UTF-8 json, make your `@Path` annotated classes extend `BaseJsonResource`.

Check out `DefaultObjectMapper` for the Jackson serialization config, and modify as necessary. As that configuration stands, I personally adopted a pattern such as this, where I create dumb POJOs for request and response serialization:

#### sample request deserialization ####
    
    {
        "searchTerms": "neon bananas",
        "maxResultSetSize" : 10
    }

deserializes from a browser into

    class ThingReq {
        public String searchTerms; // == "neon bananas"
        public Integer maxResultSetSize; // == 10
    }

#### sample response serialization ####
    
    class SearchSummaryRes {
        public List<SearchResultRes> results; // == empty list
        public Integer numResults; // == 0
    }

serializes back to a browser as

    {
        "results": [],
        "numResults": 0
    }

- - - - - - - - - -

### Hot Coding ###
What have you done with my hot coding?

#### Static "hot coding" ####
In development (a.k.a. IntelliJ), static resources serve directly from the filesystem per the default location
`classpath:static/` or the value of `-Dbase.static_dirs`. IntelliJ actually copies all the static files to a temporary build dir (called something like 'out/'). If you do not set `Dbase.static_dirs`, then you will likely have trouble live editing static files.

#### Java hot coding ####
A lot of Javanese are accustomed to being able to hot code stack frame-scoped changes. What they may not have realized is
that these are features not inherently available in any 'debugging' jvm instance. Most of us have likely been able to
hot code through various servlet containers like JBoss or Tomcat. Outside of servlet containers there's the reputedly
very good [JRebel](http://zeroturnaround.com/software/jrebel/) which apparently has strong IntelliJ support. In short,
since this isn't a WAR, and if you don't have JRebel, then you won't be able to hotcode. This has not been a problem
for me, because restarting the app takes about 3 seconds.





File Matrix
-----------
This is a reference of where various files came from to help figure what you might NOT want to delete. Anything not
specifically labelled was originally written by me, though you might never know after you're done with your copy-pasta
;) .

    .
    ├── README.md
    ├── WTFPL.txt  --------------------------------------------- license www.wtfpl.net/
    ├── build.gradle
    └── src
        ├── main
        │   ├── java
        │   │   └── ...  --------------------------------------- (omitted for brevity)
        │   ├── resources
        │   │   ├── logback.xml
        │   │   └── webdefault-nojsp.xml
        │   └── webapp
        │       ├── 404.html  ---------------------------------- HTML5 boilerplate
        │       ├── apple-touch-icon-114x114-precomposed.png  -- HTML5 boilerplate
        │       ├── apple-touch-icon-144x144-precomposed.png  -- HTML5 boilerplate
        │       ├── apple-touch-icon-57x57-precomposed.png  ---- HTML5 boilerplate
        │       ├── apple-touch-icon-72x72-precomposed.png  ---- HTML5 boilerplate
        │       ├── apple-touch-icon-precomposed.png  ---------- HTML5 boilerplate
        │       ├── apple-touch-icon.png  ---------------------- HTML5 boilerplate
        │       ├── crossdomain.xml  --------------------------- HTML5 boilerplate
        │       ├── css
        │       │   ├── base.css
        │       │   ├── bootstrap.css  ------------------------- bootstrap
        │       │   ├── bootstrap.min.css  --------------------- bootstrap
        │       │   ├── font-awesome-ie7.min.css  -------------- font-awesome
        │       │   ├── font-awesome.css  ---------------------- font-awesome
        │       │   ├── font-awesome.min.css  ------------------ font-awesome
        │       │   ├── main.css  ------------------------------ HTML5 boilerplate
        │       │   └── normalize.css  ------------------------- HTML5 boilerplate
        │       ├── doc
        │       │   └── ...  ----------------------------------- HTML5 boilerplate
        │       ├── favicon.ico  ------------------------------- HTML5 boilerplate
        │       ├── font
        │       │   └── ...  ----------------------------------- font-awesome
        │       ├── humans.txt  -------------------------------- HTML5 boilerplate
        │       ├── img
        │       │   ├── glyphicons-halflings-white.png  -------- bootstrap
        │       │   └── glyphicons-halflings.png  -------------- bootstrap
        │       ├── index.html  -------------------------------- HTML5 boilerplate
        │       ├── js
        │       │   ├── base.js
        │       │   ├── bootstrap.js  -------------------------- bootstrap
        │       │   ├── bootstrap.min.js  ---------------------- bootstrap
        │       │   ├── jquery.js  ----------------------------- jQuery
        │       │   ├── jquery.min.js  ------------------------- jQuery
        │       │   ├── main.js  ------------------------------- HTML5 boilerplate
        │       │   ├── plugins.js  ---------------------------- HTML5 boilerplate
        │       │   └── yui_util.js
        │       └── robots.txt  -------------------------------- HTML5 boilerplate
        └── test
            └── ...  ------------------------------------------- (omitted for brevity)





TO DO
-----
Client-side "templating" in Javascript, because I don't want any server-side rendering.