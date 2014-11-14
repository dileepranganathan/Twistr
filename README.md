mortar-pig-java-template
========================

Template Project for Creating new Java Loaders and UDFs for Pig

Includes three template Loader classes, and one example each of a Loader and a UDF.

* TemplateLoader: Very basic template for Pig Loader
* TemplateLoaderPushProjection: Loader template that includes implementation of push projection
* TemplateLoaderStaticSchema: Template for Loader that has a defined and static schema.  Also implements push projection.
* ExampleLoader: Loader example that loads tab-separated data.
* IdentityUDF: Example UDF that takes a tuple and returns that same tuple.


Build
=====
To run tests and create jar:

    mvn clean install
