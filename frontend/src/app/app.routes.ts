import { Routes } from '@angular/router';
import { BioskopComponent } from './components/main/bioskop-component/bioskop-component.component';
import { SalaComponent } from './components/main/sala-component/sala-component.component';
import { FilmComponent } from './components/main/film-component/film-componet.component';
import { RezervacijaComponent } from './components/main/rezervacija-component/rezervacija-component.component';
import { HomeComponent } from './components/utility/home-component/home-component.component';
import { AuthorComponent } from './components/utility/author-component/author-component.component';
import { AboutComponent } from './components/utility/about-component/about-component.component';

export const routes: Routes = [
  { path: 'bioskop', component: BioskopComponent },
  { path: 'sala', component: SalaComponent },
  { path: 'film', component: FilmComponent },
  { path: 'rezervacija', component: RezervacijaComponent },
  
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'author', component: AuthorComponent },

  // Početna ruta
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];
