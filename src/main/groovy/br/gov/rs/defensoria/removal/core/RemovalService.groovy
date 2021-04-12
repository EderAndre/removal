package br.gov.rs.defensoria.removal.core

import br.gov.rs.defensoria.removal.core.util.AntiquityComparator
import br.gov.rs.defensoria.removal.domain.Office
import br.gov.rs.defensoria.removal.domain.Person

class RemovalService {
	
	boolean isChoosable(List<Office> choosable, Office pretension) {
		Office office = choosable.find {it.id == pretension.id}
		return office != null
	}
	
	boolean willSwap(Office pretension, Person candidate) {
		Person actual = pretension.getOccupant()
		if (actual != null) {
			if (!actual.getAnalyzed()) {
			    if (actual.getAntiquity() > candidate.getAntiquity()) {
					if (actual.getAllocation().getPretender() == null) {
						if (!actual.pretensions.empty) {
							if (actual.getPretensions().first() == candidate.getAllocation()) {
								if (candidate.getAllocation().getPretender() == null) {
									if (!candidate.getAllocation().getBlocked()) {
								        return true									
									}
								}
							}
						}
					}
				}				
			}
		}
		return false
	}
	
	void swap(List<Office> choosable, Office pretension, Person candidate) {
		Person actual = pretension.getOccupant()
		Office actualPretension = actual.getPretensions().first()
				
		pretension.setPretender(candidate)
		actualPretension.setPretender(actual)
		
		pretension.setBlocked(true)
		actualPretension.setBlocked(true)
								
		actual.setAnalyzed(true)
		candidate.setAnalyzed(true)
		
		pretension.setSwapResult(true)
		actualPretension.setSwapResult(true)
	}
	
	List<Office> getNewAllocations(List<Office> choosable, List<Person> candidates) {
		List<Office> allocations = new ArrayList<Office>()
		AntiquityComparator antiquityComparator = new AntiquityComparator()
		candidates.sort(antiquityComparator)
		
		boolean finished = false
		while (!finished) {
			finished = true
			candidates.find { candidate->
				if (!candidate.getAnalyzed()) {
					boolean analysedPretensionsNoAlloc = true
					candidate.getPretensions().find { pretension->
						boolean willSwap = willSwap(pretension, candidate)
						if (willSwap) {
							swap(choosable, pretension, candidate)
							analysedPretensionsNoAlloc = false
							return true
						} else if ((isChoosable(choosable, pretension)) && (!pretension.getBlocked()) && (pretension.getPretender() == null)) {
						    pretension.setPretender(candidate)
							analysedPretensionsNoAlloc = false
							return true
						}
						return false
					}
					if (analysedPretensionsNoAlloc) {
						candidate.setAnalyzed(true)						
						candidate.getAllocation()?.setBlocked(true)
						choosable.each {
							if (!it.getSwapResult()) {
								it.setPretender(null)			
							}
						}
						finished = false
						return true
					}
				}
				return false
			}
		}
		
		choosable.each {
			allocations.add(new Office(id: it.getId(), name: it.getName(), occupant: it.getOccupant(), pretender: it.getPretender(), hasOwner:it.getHasOwner()))
		}
		
		return allocations
	}
}
