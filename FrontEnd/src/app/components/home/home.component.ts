import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  standalone: true,
  template: `
    <div class="text-center py-5">
      <h1 class="display-5 fw-bold text-primary">Bem-vindo ao Sistema de Cadastro de Livros</h1>
      <p class="lead mt-4">TJ - Teste Técnico</p>
      <hr class="my-5">
      <button class="btn btn-danger btn-lg px-5" (click)="gerarRelatorio()">
        Gerar Relatório PDF por Autor
      </button>
    </div>
  `
})
export class HomeComponent {
  constructor(private http: HttpClient) {}

  gerarRelatorio() {
    this.http.get('http://localhost:8080/api/relatorio/livros-por-autor', 
      { responseType: 'blob' })
      .subscribe(blob => {
        const url = window.URL.createObjectURL(blob);
        window.open(url, '_blank');
      });
  }
}