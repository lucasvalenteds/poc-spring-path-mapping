PORT = 8080

API_URL = http://localhost:$(PORT)

SLASH_ENCODED = %2F

# 00:15:32.865 [main] INFO  com.example.spring.Main - Server running on port 8080
# 00:22:43.222 [reactor-http-epoll-4] INFO  com.example.spring.car.CarController - Searching cars using a query text: truck
# 00:22:43.252 [reactor-http-epoll-1] INFO  com.example.spring.car.CarController - Searching cars by release year range: between 2000 and 2020
# 00:22:43.287 [reactor-http-epoll-2] INFO  com.example.spring.customer.CustomerController - Returning all customers
# 00:22:43.319 [reactor-http-epoll-3] INFO  com.example.spring.customer.CustomerController - Returning customer with ID abc/123
# 00:22:43.351 [reactor-http-epoll-4] INFO  com.example.spring.customer.CustomerController - Returning cars from customer with ID abc/123
# 00:22:43.373 [reactor-http-epoll-1] INFO  com.example.spring.customer.CustomerController - Returning car with ID xyz/102 from customer with ID abc/123
default: \
		test-cars-full-text test-cars-release-year \
		test-customers test-customer test-customer-cars test-customer-car

# [
#  {
#    "id": "xyz/100",
#    "brand": "Ford",
#    "category": "truck",
#    "releaseYear": 2005
#  },
#  {
#    "id": "xyz/101",
#    "brand": "Toyota",
#    "category": "truck",
#    "releaseYear": 1979
#  }
# ]
test-cars-full-text:
	@curl --silent $(API_URL)/cars/search/full-text/truck | jq

# [
#   {
#     "id": "xyz/100",
#     "brand": "Ford",
#     "category": "truck",
#     "releaseYear": 2005
#   },
#   {
#     "id": "xyz/102",
#     "brand": "Toyota",
#     "category": "compact",
#     "releaseYear": 2016
#   }
# ]
test-cars-release-year:
	@curl --silent $(API_URL)/cars/search/release-year/2000-2020 | jq

# [
#   {
#     "id": "abc/123",
#     "name": "John Smith",
#     "age": 45
#   },
#   {
#     "id": "def/123",
#     "name": "Mary Jane",
#     "age": 32
#   },
#   {
#     "id": "def/456",
#     "name": "Paul Anderson",
#     "age": 18
#   }
# ]
test-customers:
	@curl --silent $(API_URL)/customers | jq

# {
#   "id": "abc/123",
#   "name": "John Smith",
#   "age": 45
# }
test-customer:
	@curl --silent $(API_URL)/customers/abc$(SLASH_ENCODED)123 | jq

# [
#   {
#     "id": "xyz/102",
#     "brand": "Toyota",
#     "category": "compact",
#     "releaseYear": 2016
#   }
# ]
test-customer-cars:
	@curl --silent $(API_URL)/customers/abc$(SLASH_ENCODED)123/cars | jq

# {
#   "id": "xyz/102",
#   "brand": "Toyota",
#   "category": "compact",
#   "releaseYear": 2016
# }
test-customer-car:
	@curl --silent $(API_URL)/customers/abc$(SLASH_ENCODED)123/cars/xyz$(SLASH_ENCODED)102 | jq

