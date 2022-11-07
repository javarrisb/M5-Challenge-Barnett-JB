package com.trilogyed.gamestoreinvoicing.invoice.util.feign;


import com.trilogyed.gamestoreinvoicing.invoice.viewModel.ConsoleViewModel;
import com.trilogyed.gamestoreinvoicing.invoice.viewModel.GameViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "gamestore-catalog")
public interface CatalogClient {

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public List<GameViewModel> getAllGames();




}
