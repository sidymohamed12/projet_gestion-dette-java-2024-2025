package com.dette.views.viewspe;

import com.dette.core.View;
import com.dette.entities.Dette;
import com.dette.entities.Payement;

public interface IPayementView extends View<Payement>{
    void listePayementsDette(Dette dette);
}
