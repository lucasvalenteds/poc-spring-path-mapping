PORT = 8080

API_URL = http://localhost:$(PORT)

SLASH_ENCODED = %2F

default: test-customers test-customer test-customer-cars test-customer-car

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
	@curl -sq $(API_URL)/customers | jq

# {
#   "id": "abc/123",
#   "name": "John Smith",
#   "age": 45
# }
test-customer:
	@curl -sq $(API_URL)/customers/abc$(SLASH_ENCODED)123 | jq

# [
#   {
#     "id": "xyz/102",
#     "brand": "Toyota",
#     "category": "compact",
#     "releaseYear": 2016
#   }
# ]
test-customer-cars:
	@curl -sq $(API_URL)/customers/abc$(SLASH_ENCODED)123/cars | jq

# {
#   "id": "xyz/102",
#   "brand": "Toyota",
#   "category": "compact",
#   "releaseYear": 2016
# }
test-customer-car:
	@curl -sq $(API_URL)/customers/abc$(SLASH_ENCODED)123/cars/xyz$(SLASH_ENCODED)102 | jq

