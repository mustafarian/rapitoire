Rapitoire
=========

This is a small library that helps you generate JSON description documents of objects you use as your `@RequestBody` in a Spring based REST Api.


Getting Started
---------------

### Built with Maven

To build the library and install it in your local repository run the following maven command

`mvn clean install`


### Using the library

- Add the following to your maven build:

```xml
<dependency>
  <groupId>com.drestive</groupId>
  <artifactId>rapitoire</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

- Annotate your REST Api `@RequestBody` classes

```java
@Group(name = "productCategory")
public class ProductCategoryDto implements Serializable {

    @Element(type = ElementType.STRING, label = "Category Id", permitWriteFor = {PermitWriteOptions.NONE})
    private String id;

    @Element(type = ElementType.SELECT, label = "Parent Category", source = "category", ref = ProductCategory.class)
    private NamedEntityDto productCategory;

    @Element(type = ElementType.SELECT, label = "Category Type", required = true,
            options = "ProductCategoryType.all", ref = ProductCategoryType.class)
    @NotNull
    private NamedEntityDto productCategoryType;

    @Element(label = "Name", required = true)
    @NotNull
    @Size(min = 5, max = 100)
    private String categoryName;

    @Element(label = "Description")
    private String description;

    @Element(type = ElementType.TEXT, label = "Long Description",
            includeFor = IncludeOptions.EXCEPT_LIST)
    private String longDescription;

    @Element(type = ElementType.URL, label = "Image Url", groupLabel = "Images",
            includeFor = IncludeOptions.EXCEPT_LIST)
    private String categoryImageUrl;

    @Element(type = ElementType.BOOLEAN, label = "Show in Select",
            includeFor = IncludeOptions.EXCEPT_LIST)
    private Boolean showInSelect;

    @Element(type = ElementType.DATE, label = "Created On",
            includeFor = {IncludeOptions.UPDATE, IncludeOptions.READ, IncludeOptions.LIST}, permitWriteFor = {
            PermitWriteOptions.NONE})
    private Date createdOn;

   // setters and getters here
}
```
- Create a `RecordDesciptorController` that will respond to request from clients to describe a REST method. This can simply extend `AbstractRecordDescriptorController` from the library.

Example   

```java
@RestController
@RequestMapping(RecordDescriptorController.SERVICE_PATH_ROOT)
public class RecordDescriptorController extends AbstractRecordDescriptorController {
    public static final String SERVICE_PATH_ROOT = "/api/describe";
}
```

- Import the library's configuration in your application configuration. This is needed to help spring find the service beans defined in the library.

- Finally, your REST controller code will use the DTO described above as a @RequestBody.

```java
@RestController
@RequestMapping("/inventory")
public class CategoryController {
    @RequestMapping(path = "categories", method = RequestMethod.GET)
    @ResponseBody
    @ReturnsList
    @JsonView(Views.Read.class)
    public HttpEntity<PagedResources<Resource<ProductCategoryDto>>> categories(
    @RequestBody(required = false) ProductCategoryDto productCategoryDto, Pageable pageable,
        PagedResourcesAssembler<ProductCategoryDto> assembler) {
        return list(productCategoryDto, pageable, assembler);
    }

    @RequestMapping(path = "category", method = RequestMethod.POST)
    @ResponseBody
    @JsonView(Views.Write.class)
    public ResponseEntity<? extends ProductCategoryDto> createCategory(@Valid @RequestBody ProductCategoryDto productCategoryDto, BindingResult bindingResult)
            throws MethodArgumentNotValidException {
        return create(productCategoryDto, bindingResult);
    }
}
```

### Requesting the API Call Description from the Client  
To get a descrption of the input object (`@RequestBody`) for the above `createCategory` you can issue the following http request.

`http://localhost:8080/root/api/describe?path=%2Finventory%2Fcategory&method=post`
