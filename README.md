Jersey Bootstrap
================
also known as "Texas Bootstrap" and "Double Bootstrap Rainbow".

This project exists solely to be shamelessly cloned, forked, gutted, deleted, and so on because bootstrapping a Java
web application is of the smaller varieties of fun. *This repo itself is not a new framework*, though it is based on a lightly configured [Jersey](http://jersey.java.net/).

I apologize for using the F-word (f----work). It will not happen again.



Quickstart Development
----------------------

This is oriented towards IntelliJ.

    git clone https://github.com/dirkraft/jersey-bootstrap.git
    cd jersey-bootstrap
    gradle idea

  * Create a run configuration on `RunServer.main` with VM options `-Dbase.static_dirs=src/main/webapp/static/` and run it.

  * Navigate to [localhost:8080](localhost:8080) in your browser. You should be redirected to `index.html`. This is an
    example of static content.

  * Navigate to the sample HTTP JSON resource: `DynamicPropsWeb.java` at [localhost:8080/_sys/props](localhost:8080/_sys/props).
    This is an example of a HTTP JSON Resource.

  * Get to it! Follow [Bootstrappin'](#bootstrappin) instructions and delete anything you don't care about with the
    [File Matrix](#file-matrix) guide.




* * * * * *

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

 - **Jetty server** - [(link)](http://jetty.codehaus.org/jetty/) embedded into the fat jar build
 - **Jersey** - [(link)](http://jersey.java.net/) set up to enable the following. It may be useful to examine `RunServer.run()` at how Jetty and Jersey are configured from the get-go.
    * Serves up **embedded or external static resources**. URLs match jersey resources (controllers, REST endpoints, ...) first. If there is not one, falls back to static resources.
      + more on `-Dbase.static_dirs` under [[As a developer...]](#as-a-developer)
    * Basic [**DI/IoC**](http://en.wikipedia.org/wiki/Inversion_of_control). All resources (classes annotated `@Provider`, `@Path`, others...) are singletons. DI via `@InjectParam` annotated arguments.
    * **Jackson json** serialization. Check out `DefaultObjectMapper` for Jackson config.
    * **UTF-8** everything, as long as you make sure all your `@Path` classes extend `BaseJsonResource`. Uncaught exceptions are also generally converted into JSON.
 - **fat jar** build - gradle plugin to build an all-in-one runnable jar [(link)](https://github.com/musketyr/gradle-fatjar-plugin)
 - **JSP's disabled** - so you won't be tempted to use it or anything that depends on it, ever. If you want these, go away.
 - Bonus! **DynamicPropsWeb.java** - a property management web resource which gives you an HTTP JSON means of inspecting and changing system properties. If nothing else, this is an
   example web resource, and illustration of the [props-live](https://github.com/dirkraft/props-live) library (which I also wrote). Note that you can actually trigger
   a restart of Jetty by changing any of the system properties to which Jetty is subscribed (see RunServer.propSet).


#### You get the following browser-side things ####

 - **bootstrap.css v2.2.2** - the one and only [Twitter Bootstrap](http://twitter.github.com/bootstrap/)
 - **font-awesome.css v3.0.2** - is awesome because the icons are just font characters, and so can be colored or displayed at any
                        size. [(link)](http://fortawesome.github.com/Font-Awesome/)
 - **jQuery v1.8.3** - If you don't know what this is... I don't even know what they would do to you. [Them.](http://cl.jroo.me/z3/U/C/k/e/a.baa-Them-Kittens.jpg)
 - parts of **HTML5 Boilerplate v4.1.0** - I included a few files from [HTML5 boilerplate](http://html5boilerplate.com/).
   Dumping the whole thing into the static file structure makes it feel messy before you've even started.

Refer to the [[File Matrix section]](#file-matrix) and each external source's site and documentation to act upon said
resources.





Bootstrappin'
-------------
Pretty much you'll want to do a full project text search for ∏ (that's capital π). Anywhere that character appears
is something that you'll want to change to Make It Yours (including this README.md)! These are the minimum required to
erase all significant traces that you stole this code, specifically, from me.

 1. root directory - rename from 'jersey-bootstrap'
 2. `build.gradle` - group name and fatJar Main-Class location
 3. delete the two `placeholder` files in src/test/java and src/test/resources
 4. package structure - move everything from 'com/github/dirkraft/jerseyboot' to 'com/mega/corp/imaedthismyslef'

Then you'll want to decide what you want to keep, lose, or modify from the various static resources sources, and make
plans to replace any such placeholders. The [[File Matrix section]](#file-matrix) may help with this. Refer to each's
own site and documentation for details.

Once that's all done, you'll want to quit IntelliJ and run yourself one final `gradle idea` before making Exodus of
this README.md (delete it or replace it with your own). Stealing code has never been so easy! Don't feel bad, I'm pretty
sure I just stole all of this from someone else. Pay it forward.





License
-------
Jersey Bootstrap is licensed under WTFPL v2. A copy is available in the project, [WTFPL.txt](WTFPL.txt). You should
DWTFYW with this file (like deleting it) if you don't want your derivative work to be subject to the same license.



* * * * * * * * * 



As a developer...
=================
(I tire of the Agile methodology) I configure my IDE with useful settings.


### Running/Debugging the App ###

 1. determine the value for `-Dbase.static_dirs`
   * in a single web module project structure, just

            -Dbase.static_dirs=src/main/webapp/static/
   * in a multi-module web module project structure first-one-wins order of static directories, e.g.

            -Dbase.static_dirs=src/main/webapp/static/;../base-webapp/src/main/webapp/static/

 2. run or debug `RunServer.main` with that system property. `-Dbase.static_dirs` is normally only useful for development.



Development Tips
----------------
This entire section outlines development strategies and paradigms that I have found effective in keeping things simple. I have found that I can fit all of my own needs into these constraints and thus benefit from the assumptions and commonalities provided by this bootstrapping project. If any of the contained developmental conventions are unacceptable to you, then you may discard them at will. But realize that jersey-bootstrap exists as a jumpstart to an application to these ends.

In summary, the points are:
 - `@Path` annotates HTTP JSON classes which should extend `BaseJsonResource`
 - `@Provider` annotates Internal Service Classes
 - inject dependencies through `@InjectParam`-annotated constructor args
   * neither circular dependency injection nor implicit argument types are supported by Jersey's simple DI mechanism
 - Use system properties for all configurable elements.


- - - - - - - -

There are only two kinds of classes in the app:

 - those that face the world, and
 - those that don't.

Each are known by many names, but in the scope of this jersey-bootstrap project they are respectively [[JSON HTTP Classes]](#json-http-classes) and [[Internal Service Classes]](#internal-service-classes).

### JSON HTTP classes ###
a.k.a. controllers, web resources, web endpoints, REST services, etc.

#### class construction ####

If you want something serialized to and from UTF-8 json, make your `@Path` annotated classes extend `BaseJsonResource`. Use `@InjectParam` for dependencies. jersey-bootstrap is set up to use a `SingletonFactory` in `RunServer`when doing DI, so that you never end up with more than one instance of any resource. Example

```java
@Path("/sardines")
public class SardinesWeb extends BaseJsonResource {

    private final SardinesService sardinesService;

    public SardinesWeb(@InjectParam SardinesService sardinesService) {
        this.sardinesService = sardinesService;
    }

    @Path("count")
    public int countSardines() {
        return sardineService.getTotalSardines();
    }

    @Path("{id}")
    public Sardine getSardine(@PathParam("id") String id) {
        return sardineService.findById(id);
    }
}
```


Check out `DefaultObjectMapper` for the Jackson serialization config, and modify if necessary though I encourage you to leave it as is. As that configuration stands, one way you might deal with requests and responses might be with simple POJOs like so...

#### sample request deserialization ####

```javascript
{
    "searchTerms": "neon bananas",
    "maxResultSetSize" : 10
}
```

deserializes from a browser into

```java
class ThingReq {
    public String searchTerms; // == "neon bananas"
    public Integer maxResultSetSize; // == 10
}
```

#### sample response serialization ####

```java
class SearchSummaryRes {
    public List<SearchResultRes> results; // == empty list
    public Integer numResults; // == 0
}
```

serializes back to a browser as

```javascript
{
    "results": [],
    "numResults": 0
}
```


### Internal Service Classes ###
a.k.a. service beans, DAO classes, resources, providers, etc.

#### class construction ####

Annotate classes with `@Provider` that are not going to be serving JSON HTTP requests. The following example assumes something like a three-tiered architecture (controllers, services, DAOs), but is strictly for illustration.

```java
@Provider
public class SardinesService {

    private final SardineDao sardineDao;

    public SardinesService(@InjectParam SardineDaoRedis sardineDaoRedis) {
        // Assume SardineDaoRedis is a redis implementation of SardineDao interface.
        // Caution that jersey's built-in DI mechanic would not be able to resolve
        // that there is one implementor of SardineDao. So the constructor argument here
        // explicitly types to SardineDaoRedis.
        this.sardineDao = sardineDao;
    }
}
```

Note that jersey's built-in dependency injection mechanic is primitive and does not support
circular dependency injection like Spring.


### External Configuration ###
In your application code, look up configuration through system properties. Don't use main(String[] **arguments**).
Why? Here's just a few reasons:

 - order is NOT important with system properties as they are with arguments
   * Some developer haphazardly shuffles the order of the arguments of a main method. Now all your automation and
     deployment scripts are broken.
 - `-DmyApp.thread_pool.max_size=16` is much more expressive and documented than `16`
 - The given `DynamicPropsWeb` JSON resource already gives you a straightforward, minimal way to inspect (and change)
   configuration.

On top of that, this bootstrap includes my property lookup-easing library called
[props-live](https://github.com/dirkraft/props-live). It gives you some convenient parsing functions (e.g. getString,
getDouble, ...) as well as property change notification. Jetty itself can be triggered to restart on certain property
changes.

 - `jetty.port` : change the port jetty is listening on
 - `jetty.restart` : post any value to this property to trigger a jetty restart. Useful if you've changed some other
   properties and would prefer to reinitialize the whole application.
 - `base.static_dirs` : change the location of static files


### Client-side Templating ###
Wait, you took away my PHP-style JSP's!

I don't really like templating languages, but *includes* themselves are a simple and powerful concept. I've included
a browser-side utility for achieving includes which suits my needs. Unfortunately because it is client-side, page loads
can feel like regurgitating kittens, but this has not annoyed me nearly enough to opt for a full-blown templating
engine.

One such example lives in the sample `index.html`

```html
<script type="text/javascript" src="js/includes.js"></script>

<include>include/navigation.html</include>

<div>
    content
</div>

<script type="text/javascript">
loadIncludes(); // optionally takes a callback after loading all template content
</script>

```

A pretty simple answer to 'page flow' concerns would be to introduce an *html compile* step to unroll all
such `<include>` tags. Some of the pros out there already compile their html and css (wtf?). It's really not
far-fetched to have such a *productionizing* step.


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





Deployment
----------
haha. ha.

 - build `gradle fatJar`
 - copy the jar from build/libs/your-fat.jar to your production servers
 - java [-D system properties] -jar your-fat.jar





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