package project.imaginarium.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Offer;
import project.imaginarium.data.models.offers.Tag;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, String> {

    Optional<Offer> findByName(String name);
    List<Offer> findAllBySector(Sector sector);
    List<Offer> findAllByTagsContains(Tag tag);
}
