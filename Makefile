PORT = 8080

API_URL = http://localhost:$(PORT)

SLASH_ENCODED = %2F

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
	@curl -sq $(API_URL)/cars/search/full-text/truck | jq

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
	@curl -sq $(API_URL)/cars/search/release-year/2000-2020 | jq

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

