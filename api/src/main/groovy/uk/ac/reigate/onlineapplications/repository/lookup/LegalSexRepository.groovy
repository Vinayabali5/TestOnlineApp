package uk.ac.reigate.onlineapplications.repository.lookup

import org.springframework.data.repository.CrudRepository

import uk.ac.reigate.onlineapplications.domain.lookup.LegalSex

interface LegalSexRepository extends CrudRepository<LegalSex, Integer> {
}
