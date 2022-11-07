package com.trilogyed.gamestorecatalog.catalog.service;

import com.trilogyed.gamestorecatalog.catalog.repository.*;
import com.trilogyed.gamestorecatalog.catalog.model.*;
import com.trilogyed.gamestorecatalog.catalog.viewModel.ConsoleViewModel;
import com.trilogyed.gamestorecatalog.catalog.viewModel.GameViewModel;
import com.trilogyed.gamestorecatalog.catalog.viewModel.TShirtViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class GameStoreCatalogServiceLayerTest {

    ConsoleRepository consoleRepository;
    GameRepository gameRepository;
    TShirtRepository tShirtRepository;
    GameStoreCatalogServiceLayer service;

    @Before
    public void setUp() throws Exception {
        //setUpConsoleRepositoryMock();
        //setUpGameRepositoryMock();
        //setUpTShirtRepositoryMock();

        service = new GameStoreCatalogServiceLayer(
                gameRepository, consoleRepository, tShirtRepository);
    }

    //Testing Console Operations...
    @Test
    public void shouldCreateGetConsole() {

        ConsoleViewModel console = new ConsoleViewModel();
        console.setModel("Playstation");
        console.setManufacturer("Sony");
        console.setMemoryAmount("120gb");
        console.setProcessor("Intel I7-9750H");
        console.setPrice(new BigDecimal("299.99"));
        console.setQuantity(4);
        console = service.createConsole(console);

        ConsoleViewModel console1 = service.getConsoleById(console.getId());
        assertEquals(console, console1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenCreateConsoleWithNullViewModel() {

        ConsoleViewModel console = new ConsoleViewModel();

        console = null;
        console = service.createConsole(console);
    }

    @Test
    public void shouldUpdateConsole() {
        ConsoleViewModel console2 = new ConsoleViewModel();
        console2.setModel("Playstation");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("120gb");
        console2.setProcessor("Intel I7-9750H");
        console2.setPrice(new BigDecimal("299.99"));
        console2.setQuantity(4);
        console2 = service.createConsole(console2);

        console2.setQuantity(6);
        console2.setPrice(new BigDecimal(289.99));

        service.updateConsole(console2);

        verify(consoleRepository, times(2)).save(any(Console.class));

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailUpdateConsoleWithNullModelView() {
        ConsoleViewModel console2 = null;

        service.updateConsole(console2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenUpdateConsoleWithBadId() {
        ConsoleViewModel console2 = new ConsoleViewModel();
        console2.setModel("Playstation");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("120gb");
        console2.setProcessor("Intel I7-9750H");
        console2.setPrice(new BigDecimal("299.99"));
        console2.setQuantity(4);
        console2 = service.createConsole(console2);

        console2.setQuantity(6);
        console2.setPrice(new BigDecimal(289.99));

        //change Id to an invalid one.
        console2.setId(console2.getId()+1);

        service.updateConsole(console2);
    }

    @Test
    public void shouldDeleteConsole() {

        ConsoleViewModel console2 = new ConsoleViewModel();
        console2.setModel("Playstation");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("120gb");
        console2.setProcessor("Intel I7-9750H");
        console2.setPrice(new BigDecimal("299.99"));
        console2.setQuantity(4);
        console2 = service.createConsole(console2);

        service.deleteConsole(console2.getId());

        verify(consoleRepository).deleteById(console2.getId());
    }

    @Test
    public void shouldFindConsoleByManufacturer() {
        List<ConsoleViewModel> cvmList = new ArrayList<>();

        ConsoleViewModel console2 = new ConsoleViewModel();
        console2.setModel("Playstation");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("120gb");
        console2.setProcessor("Intel I7-9750H");
        console2.setPrice(new BigDecimal("299.99"));
        console2.setQuantity(4);

        console2 = service.createConsole(console2);
        cvmList.add(console2);

        ConsoleViewModel console3 = new ConsoleViewModel();
        console3.setModel("Xbox");
        console3.setManufacturer("Sony");
        console3.setMemoryAmount("256gb");
        console3.setProcessor("Intel I7-9750H");
        console3.setPrice(new BigDecimal("399.99"));
        console3.setQuantity(4);

        console3 = service.createConsole(console3);
        cvmList.add(console3);

        List<ConsoleViewModel> cvmFromService = service.getConsoleByManufacturer("Sony");

        assertEquals(cvmList, cvmFromService);
    }

    @Test
    public void shouldFindAllConsoles() throws Exception{
        List<ConsoleViewModel> cvmList = new ArrayList<>();

        ConsoleViewModel console1 = new ConsoleViewModel();
        console1.setModel("Playstation");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("120gb");
        console1.setProcessor("Intel I7-9750H");
        console1.setPrice(new BigDecimal("299.99"));
        console1.setQuantity(4);

        console1 = service.createConsole(console1);
        cvmList.add(console1);

        ConsoleViewModel console2 = new ConsoleViewModel();
        console2.setModel("Xbox");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("256gb");
        console2.setProcessor("Intel I7-9750H");
        console2.setPrice(new BigDecimal("399.99"));
        console2.setQuantity(4);

        console2 = service.createConsole(console2);
        cvmList.add(console2);

        ConsoleViewModel console3 = new ConsoleViewModel();
        console3.setModel("PS III");
        console3.setManufacturer("Sony");
        console3.setMemoryAmount("512Gb");
        console3.setProcessor("AMD I7-9750A");
        console3.setPrice(new BigDecimal("199.99"));
        console3.setQuantity(40);

        console3 = service.createConsole(console3);
        cvmList.add(console3);

        List<ConsoleViewModel> cvmFromService = service.getAllConsoles();

        assertEquals(cvmList.size(), cvmFromService.size());
    }
    //TSHIRT SERVICE LAYER

    public TShirtViewModel createTShirt(TShirtViewModel tShirtViewModel) {

        // Remember model view has already been validated through JSR 303
        // Validate incoming TShirt Data in the view model
        if (tShirtViewModel==null) throw new IllegalArgumentException("No TShirt is passed! TShirt object is null!");

        TShirt tShirt = new TShirt();
        tShirt.setSize(tShirtViewModel.getSize());
        tShirt.setColor(tShirtViewModel.getColor());
        tShirt.setDescription(tShirtViewModel.getDescription());
        tShirt.setPrice(tShirtViewModel.getPrice());
        tShirt.setQuantity(tShirtViewModel.getQuantity());

        tShirt = tShirtRepository.save(tShirt);

        return buildTShirtViewModel(tShirt);
    }

    public TShirtViewModel getTShirt(long id) {
        Optional<TShirt> tShirt = tShirtRepository.findById(id);
        if (tShirt == null)
            return null;
        else
            return buildTShirtViewModel(tShirt.get());
    }

    public void updateTShirt(TShirtViewModel tShirtViewModel) {

        // Remember model view has already been validated through JSR 303
        // Validate incoming TShirt Data in the view model
        if (tShirtViewModel==null) throw new IllegalArgumentException("No TShirt is passed! TShirt object is null!");

        //make sure the Console exists. and if not, throw exception...
        if (this.getTShirt(tShirtViewModel.getId())==null)
            throw new IllegalArgumentException("No such TShirt to update.");

        TShirt tShirt = new TShirt();
        tShirt.setId(tShirtViewModel.getId());
        tShirt.setSize(tShirtViewModel.getSize());
        tShirt.setColor(tShirtViewModel.getColor());
        tShirt.setDescription(tShirtViewModel.getDescription());
        tShirt.setPrice(tShirtViewModel.getPrice());
        tShirt.setQuantity(tShirtViewModel.getQuantity());

        tShirtRepository.save(tShirt);
    }

    public void deleteTShirt(long id) {

        tShirtRepository.deleteById(id);
    }

    public List<TShirtViewModel> getTShirtByColor(String color) {
        List<TShirt> tShirtList = tShirtRepository.findAllByColor(color);
        List<TShirtViewModel> tvmList = new ArrayList<>();

        if (tShirtList == null)
            return null;
        else
            tShirtList.stream().forEach(t -> tvmList.add(buildTShirtViewModel(t)));
        return tvmList;
    }

    public List<TShirtViewModel> getTShirtBySize(String size) {
        List<TShirt> tShirtList = tShirtRepository.findAllBySize(size);
        List<TShirtViewModel> tvmList = new ArrayList<>();

        if (tShirtList == null)
            return null;
        else
            tShirtList.stream().forEach(t -> tvmList.add(buildTShirtViewModel(t)));
        return tvmList;
    }

    public List<TShirtViewModel> getAllTShirts() {
        List<TShirt> tShirtList = tShirtRepository.findAll();
        List<TShirtViewModel> tvmList = new ArrayList<>();

        if (tShirtList == null)
            return null;
        else
            tShirtList.stream().forEach(t -> tvmList.add(buildTShirtViewModel(t)));
        return tvmList;
    }

    //Helper Methods...

    public ConsoleViewModel buildConsoleViewModel(Console console) {
        ConsoleViewModel consoleViewModel = new ConsoleViewModel();
        consoleViewModel.setId(console.getId());
        consoleViewModel.setModel(console.getModel());
        consoleViewModel.setManufacturer(console.getManufacturer());
        consoleViewModel.setMemoryAmount(console.getMemoryAmount());
        consoleViewModel.setProcessor(console.getProcessor());
        consoleViewModel.setPrice(console.getPrice());
        consoleViewModel.setQuantity(console.getQuantity());

        return consoleViewModel;
    }

    public GameViewModel buildGameViewModel(Game game) {

        GameViewModel gameViewModel = new GameViewModel();
        gameViewModel.setId(game.getId());
        gameViewModel.setTitle(game.getTitle());
        gameViewModel.setEsrbRating(game.getEsrbRating());
        gameViewModel.setDescription(game.getDescription());
        gameViewModel.setPrice(game.getPrice());
        gameViewModel.setStudio(game.getStudio());
        gameViewModel.setQuantity(game.getQuantity());

        return gameViewModel;
    }

    public TShirtViewModel buildTShirtViewModel(TShirt tShirt) {
        TShirtViewModel tShirtViewModel = new TShirtViewModel();
        tShirtViewModel.setId(tShirt.getId());
        tShirtViewModel.setSize(tShirt.getSize());
        tShirtViewModel.setColor(tShirt.getColor());
        tShirtViewModel.setDescription(tShirt.getDescription());
        tShirtViewModel.setPrice(tShirt.getPrice());
        tShirtViewModel.setQuantity(tShirt.getQuantity());

        return tShirtViewModel;
    }

}
