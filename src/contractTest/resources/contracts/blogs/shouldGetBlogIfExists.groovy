package contracts.blogs

import org.springframework.cloud.contract.spec.Contract;

Contract.make {
    description("""
Represents a successful scenario of getting a beer

```
given:
    blog with id 1 exist
when:
    user requests for blog with id 1
then:
    user gets a blog back
```

""")
    request {
        method 'GET'
        url '/blogs/1'
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body("""
            {
                "id": "1",
                "createdOn":null,
                "createdBy":null,
                "modifiedOn":null,
                "modifiedBy":null,
                "version":null,
                "title":"title",
                "content":"content",
                "author":"author"
            }
            """)
    }
}
