package project.imaginarium.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.Offer;

public interface OfferRepository extends JpaRepository<Offer, String> {

    Offer findByName(String name);
}
