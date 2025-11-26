import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LivroListComponent } from './components/livro/livro-list/livro-list.component';
import { LivroFormComponent } from './components/livro/livro-form/livro-form.component';
import { AutorListComponent } from './components/autor/autor-list/autor-list.component';
import { AutorFormComponent } from './components/autor/autor-form/autor-form.component';
import { AssuntoListComponent } from './components/assunto/assunto-list/assunto-list.component';
import { AssuntoFormComponent } from './components/assunto/assunto-form/assunto-form.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'livros', component: LivroListComponent },
  { path: 'livros/novo', component: LivroFormComponent },
  { path: 'livros/editar/:id', component: LivroFormComponent },
  { path: 'autores', component: AutorListComponent },
  { path: 'autores/novo', component: AutorFormComponent },
  { path: 'autores/editar/:id', component: AutorFormComponent },
  { path: 'assuntos', component: AssuntoListComponent },
  { path: 'assuntos/novo', component: AssuntoFormComponent },
  { path: 'assuntos/editar/:id', component: AssuntoFormComponent },
  { path: '**', redirectTo: '' }
];