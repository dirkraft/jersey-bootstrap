# NOT READY YET I'M WORKING ON IT #

Jersey Bootstrap
================
also known as "Texas Bootstrap" and "Double Bootstrap Rainbow".

This project exists solely to be shamelessly cloned, forked, gutted, deleted, and so on because bootstrapping a Java
web application is no fun.



"Features"
----------
Here's what you get regardless of what you want.

# Stuff #

But really, you got a bunch because the truth is
## It is easy and fun to delete stuff. ##
It is less fun to track down disparate dependencies.

You get the following server-side things:

 - [Jetty server](http://jetty.codehaus.org/jetty/)
 - [Jersey](http://jersey.java.net/) - set up to enable the following...
   * Serves up embedded or external static resources. URLs match jersey resources (controllers, REST endpoints, ...)
     first. If there is not one, falls back to static resources.
 - TODO documentation

You get the following browser-side things:
 - `bootstrap.css` v2.2.2 - the one and only [Twitter Bootstrap](http://twitter.github.com/bootstrap/)
 - `font-awesome.css` - is awesome because the icons are just font characters, and so can be colored or displayed at any
                        size. [font-awesome](http://fortawesome.github.com/Font-Awesome/) I just noticed the url is
                        FORTawesome.
 - `jQuery` v1.8.3 - If you don't know what this is... I don't even know what they would do to you.
 - `HTML5 Boilerplate` v4.1.0 - All I did was dump the files in, but there you go.
   [HTML5 boilerplate](http://html5boilerplate.com/)



Bootstrappin'
-------------
Pretty much you'll want to do a full project text search for ∏ (that's capital π). Anywhere that character appears
is something that you'll want to change to Make It Yours! These are the minimum required to erase all significant traces
that you stole this code.

 - root directory - rename from 'jersey-bootstrap'
 - `build.gradle` - group name
 - package structure - move everything from 'com/github/dirkraft/jerseyboot' to 'com/mega/corp/imaedthisbymyslef'
 - delete the two `placeholder` files in src/test/java and src/test/resources
 - TODO documentation

Then you'll want to decide what you want to keep and lose. The [File Matrix](#file-matrix) section at the bottom may help this this.

Once that's all done, you'll want to quit IntelliJ and run yourself one final `gradle idea` before making Exodus of
this README.md (delete it or replace it with your own). Stealing code has never been so easy!



License
-------
Jersey Bootstrap is licensed under WTFPL v2. A copy is available in the project, [WTFPL.txt](WTFPL.txt). You should
DWTFYW with this file (like deleting it) if you don't want your derivative work to be subject to the same license.



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
        │       │   ├── font-awesome.css  ---------------------- font-awesome
        │       │   ├── main.css  ------------------------------ HTML5 boilerplate
        │       │   └── normalize.css  ------------------------- HTML5 boilerplate
        │       ├── doc
        │       │   └── ...  ----------------------------------- HTML5 boilerplate
        │       ├── favicon.ico
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