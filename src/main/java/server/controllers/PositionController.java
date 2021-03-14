package server.controllers;
import server.repository.PositionRepository;
import server.model.Position;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/store")
public class PositionController {
    private final PositionRepository PosRepository;
    public PositionController(PositionRepository PosRepository) {
        this.PosRepository = PosRepository;
    }
    @GetMapping("/blackmagic")
    List<Position> doBlackMagic() {
        // один раз
//        Ticket ticket = new Ticket(1500);
//        Client client = new Client("Алексей", "Ковалев");
//        ticket.setClient(client);
//
//        this.ticketRepository.save(ticket);
//        this.clientRepository.save(client);
        Position position = new Position("Manager");
        this.PosRepository.save(position);
       // System.out.println("sdfgh");

        List<Position> allt = this.PosRepository.findAll();
        System.out.println(allt);
        return allt;
    }
}
