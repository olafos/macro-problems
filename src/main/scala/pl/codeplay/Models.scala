package pl.codeplay

/**
 * Address command
 *
 * @param `type`
 * @param streetAddress
 * @param postalCode
 * @param city
 * @param province
 * @param country
 * @param default
 */
case class CreateCompanyAddressCommand(`type`: Symbol,
                                       streetAddress: String,
                                       postalCode: String,
                                       city: String,
                                       province: Option[String] = None,
                                       country: String,
                                       default: Option[Boolean] = None)

/**
 * Address record
 *
 * @param id
 * @param revision
 * @param headRevision
 * @param groupId
 * @param userId
 * @param createdAt
 * @param streetAddress
 * @param postalCode
 * @param city
 * @param province
 * @param country
 */
case class AddressRecord(id: Id,
                         revision: Revision = 1L,
                         headRevision: Boolean = true,
                         groupId: Id,
                         userId: Id,
                         createdAt: DateTime,
                         streetAddress: String,
                         postalCode: String,
                         city: String,
                         province: Option[String] = None,
                         country: String)