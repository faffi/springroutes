springroutes
============

Dumps Spring MVC routes similar to a RoR routes file

Uses the eclipse JDT parser to search java source for Spring annotations 

Build the project with:

	mvn clean compile assembly:single

The jar will be in the 'target' directory, named something like: SpringRoutes-1.0-SNAPSHOT-jar-with-dependencies.jar 

Example output below from project: https://github.com/SpringSource/spring-mvc-showcase

Please file any bugs you find, the code isn't exactly the cleanest, but it works.

	[+] Scanning Directory: /Users/user/Desktop/spring-mvc-showcase
	Class: org.springframework.samples.mvc.async.CallableController
		ANY              /async/callable/response-body   			                                                                                                                                
		ANY              /async/callable/view            			                                                                                                                                
		ANY              /async/callable/exception       			                                                                                                                                
		ANY              /async/callable/custom-timeout-handling                                                                                                                                

	Class: org.springframework.samples.mvc.async.DeferredResultController
		ANY              /async/deferred-result/response-body                                                                                                                                
		ANY              /async/deferred-result/model-and-view                                                                                                                                
		ANY              /async/deferred-result/exception                                                                                                                                
		ANY              /async/deferred-result/timeout-value                                                                                                                                

	Class: org.springframework.samples.mvc.convert.ConvertController
		ANY              /convert/primitive              			                                                                                                                                
		ANY              /convert/date/{value}           			                                                                                                                                
		ANY              /convert/collection             			                                                                                                                                
		ANY              /convert/formattedCollection    			                                                                                                                                
		ANY              /convert/bean                   			                                                                                                                                
		ANY              /convert/value                  			                                                                                                                                
		ANY              /convert/custom                 			                                                                                                                                

	Class: org.springframework.samples.mvc.data.custom.CustomArgumentController
		[GET]            /data/custom    			                                                                                                                                

	Class: org.springframework.samples.mvc.data.RequestDataController
		[GET]            /data/param                     			                                                                                                                                
		[GET]            /data/group                     			                                                                                                                                
		[GET]            /data/path/{var}                			                                                                                                                                
		[GET]            /data/{path}/simple              			                                                                                                                                
		[GET]            /data/{path1}/{path2}             			                                                                                                                                
		[GET]            /data/header                    			                                                                                                                                
		[GET]            /data/cookie                    			                                                                                                                                
		[POST]           /data/body                      			                                                                                                                                
		[POST]           /data/entity                    			                                                                                                                                

	Class: org.springframework.samples.mvc.data.standard.StandardArgumentsController
		[GET]            /data/standard/request                                                                                                                                
		[POST]           /data/standard/request/reader                                                                                                                                
		[POST]           /data/standard/request/is                                                                                                                                
		ANY              /data/standard/response                                                                                                                                
		ANY              /data/standard/response/writer                                                                                                                                
		ANY              /data/standard/response/os                                                                                                                                
		ANY              /data/standard/session                                                                                                                                

	Class: org.springframework.samples.mvc.exceptions.ExceptionController
		ANY              /exception      			                                                                                                                                
		ANY              /global-exceptions                                                                                                                                

	Class: org.springframework.samples.mvc.fileupload.FileUploadController
		[GET]            /fileupload                     			                                                                                                                                
		[POST]           /fileupload                     			                                                                                                                                

	Class: org.springframework.samples.mvc.form.FormController
		[GET]            /form                           			                                                                                                                                
		[POST]           /form                           			                                                                                                                                

	Class: org.springframework.samples.mvc.mapping.ClasslevelMappingController
		ANY              /class-mapping/*/path           			                                                                                                                                
		[GET]            /class-mapping/*/path/*         			                                                                                                                                
		[GET]            /class-mapping/*/method         			                                                                                                                                
		[GET]            /class-mapping/*/parameter      			                                                                                                                                
		[GET]            /class-mapping/*/parameter      			                                                                                                                                
		[GET]            /class-mapping/*/header         			                                                                                                                                
		[GET]            /class-mapping/*/notheader      			                                                                                                                                
		[POST]           /class-mapping/*/consumes       			                                                                                                                                
		[GET]            /class-mapping/*/produces       			                                                                                                                                

	Class: org.springframework.samples.mvc.mapping.MappingController
		ANY              /mapping/path   			                                                                                                                                
		[GET]            /mapping/path/* 			                                                                                                                                
		[GET]            /mapping/method 			                                                                                                                                
		[GET]            /mapping/parameter                                                                                                                                
		[GET]            /mapping/parameter                                                                                                                                
		[GET]            /mapping/header 			                                                                                                                                
		[GET]            /mapping/header 			                                                                                                                                
		[POST]           /mapping/consumes                                                                                                                                
		[GET]            /mapping/produces                                                                                                                                
		[GET]            /mapping/produces                                                                                                                                

	Class: org.springframework.samples.mvc.messageconverters.MessageConvertersController
		[POST]           /messageconverters/string       			                                                                                                                                
		[GET]            /messageconverters/string       			                                                                                                                                
		[POST]           /messageconverters/form         			                                                                                                                                
		[GET]            /messageconverters/form         			                                                                                                                                
		[POST]           /messageconverters/xml          			                                                                                                                                
		[GET]            /messageconverters/xml          			                                                                                                                                
		[POST]           /messageconverters/json         			                                                                                                                                
		[GET]            /messageconverters/json         			                                                                                                                                
		[POST]           /messageconverters/atom         			                                                                                                                                
		[GET]            /messageconverters/atom         			                                                                                                                                
		[POST]           /messageconverters/rss          			                                                                                                                                
		[GET]            /messageconverters/rss          			                                                                                                                                

	Class: org.springframework.samples.mvc.redirect.RedirectController
		[GET]            /redirect/uriTemplate           			                                                                                                                                
		[GET]            /redirect/uriComponentsBuilder  			                                                                                                                                
		[GET]            /redirect/{account}             			                                                                                                                                

	Class: org.springframework.samples.mvc.response.ResponseController
		ANY              /annotation     			                                                                                                                                
		ANY              /charset/accept 			                                                                                                                                
		ANY              /charset/produce                                                                                                                                
		ANY              /entity/status  			                                                                                                                                
		ANY              /entity/headers 			                                                                                                                                

	Class: org.springframework.samples.mvc.simple.SimpleController
		ANY              /simple         			                                                                                                                                

	Class: org.springframework.samples.mvc.simple.SimpleControllerRevisited
		[GET]            /simple/revisited                                                                                                                                

	Class: org.springframework.samples.mvc.validation.ValidationController
		ANY              /validate       			                                                                                                                                

	Class: org.springframework.samples.mvc.views.ViewsController
		[GET]            /views/*/html                   			                                                                                                                                
		[GET]            /views/*/viewName               			                                                                                                                                
		[GET]            /views/*/pathVariables/{foo}/{fruit}pathVariables                                                                                                                                
		[GET]            /views/*/dataBinding/{foo}/{fruit}dataBinding                                                                                                                                


