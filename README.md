# Forelesning 4: Http server

HTTP request ser ut


## Repeat av lecture 2

* [x] Maven
* [x] Github Actions
* [x] Working branch
* [x] Pull request
* [x] Test report

## Repeat av lecture 3: Socket, HTTP

* [x] Socket connects server
* [x] Status code
* [x] Header fields
* [x] Content-length
* [x] Message body
 
## TODAY: HttpServer

* [x] HttpServer should respond with 404
* [x] HttpServer should include request target in 404
* [x] Return a static content for /hello
* [x] Content-type
* [x] Return HTML file from disk
* [x] Return <form>
* [x] Process GET request for form

## Lecture 6:

* [x] styling
* [x] handle more than one request
  * [x] Feilhåndtering
* [x] GET requests with more than one field
* [x] Refactor -> HttpMessage class
* [x] Process POST request from form
  * [ ] URL encoding


## Lecture 9:

* [ ] RoleDaoTest should list roles
* [ ] RoleController should list roles
* [ ] PersonDaoTest should retrieve saved person
* [ ] PersonDaoTest should list people
* [ ] CreatePersonController should create people
* [ ] ListPersonController should list people
* [ ] Make executable JAR
* [ ] Serve HTML from inside jar-file

## Omitted on purpose - your tasks at exam

* [ ] URL encoding (readBytes has a fundamental flaw)
* [ ] 303 See Other
* [ ] Set-Cookie / Cookie headers
* [ ] AbstractDao for insert and update
* [ ] Updating data (HTML, JavaScript and Controller)
* [ ] POST and GET with same URL (/api/people)
* [ ] 500 on exception
* [ ] http://localhost:8080 should return index.html