import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { HomeComponent } from './components/home/home.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterLink,
    RouterOutlet,
    HomeComponent
  ],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
      <div class="container-fluid">
        <a class="navbar-brand fw-bold" routerLink="/">Cadastro de Livros - TJ</a>
        <div class="navbar-nav ms-auto">
          <a class="nav-link" routerLink="/livros" routerLinkActive="active">Livros</a>
          <a class="nav-link" routerLink="/autores" routerLinkActive="active">Autores</a>
          <a class="nav-link" routerLink="/assuntos" routerLinkActive="active">Assuntos</a>
        </div>
      </div>
    </nav>

    <div class="container mt-4">
      <router-outlet></router-outlet>
    </div>
  `,
  styles: [`
    .active {
      font-weight: bold;
      border-bottom: 3px solid white;
    }
  `]
})
export class AppComponent { }