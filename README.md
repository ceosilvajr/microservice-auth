[![Build Status](https://travis-ci.org/ceosilvajr/microservice-auth.svg?branch=master)](https://travis-ci.org/ceosilvajr/microservice-auth) [![](https://jitpack.io/v/ceosilvajr/microservice-auth.svg)](https://jitpack.io/#ceosilvajr/microservice-auth)

MicroService authenticator
=======
Simple authentication for microservices using JWT and Retrofit

Download 
-------
1. Add the JitPack repository to your build file
```
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
2. Add the dependency
```
dependencies {
	  compile 'com.github.ceosilvajr:microservice-auth:+'
	}
```

How to use
-------
* Make api request
1. Define Retrofit ApiClass
``` java
public interface SymbolApi extends ApiClass {
  @GET("/fetch")
  List<Object> fetchSymbols();
}
```
2. Initialize `MicroServiceAuth` in your API Provider
``` java
public class SymbolProvider {
  public List<Object> fetchSymbol() {
    return new MicroServiceAuth<SymbolApi>(AppConfig.SYMBOL_BASE_URL.getValue())
        .provideDefaultApiClass(SymbolApi.class).fetchSymbols();
  }
}
```
* Authenticate api request using `AbstractMicroServiceFilter`
- Extend your filter class to `AbstractMicroServiceFilter` and implement `isAuthorized(final String token)` function.
- To check service authentication
``` java
public class ServiceServletFilter extends AbstractMicroServiceFilter {

  @Override
  public boolean isAuthorized(final String token){
  return isAuthorizedService(token);
  }
}
```
- To check cron authentication
``` java
public class CronServletFilter extends AbstractMicroServiceFilter {

  @Override
  public boolean isAuthorized(final String token){
  return isAuthorizedCron(token);
  }
}
```

- then declare this to your Module
``` java
filter("/cron/*").through(CronServletFilter.class);
filter("/service/*").through(ServiceServletFilter.class);
```

License
-------

MIT License

Copyright (c) 2018 Ariel Silva Jr

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
