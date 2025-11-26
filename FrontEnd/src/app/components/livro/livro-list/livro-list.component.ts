import { Component, OnInit } from '@angular/core';
import { LivroService } from '../../../services/livro.service';
import { Livro } from '../../../models/livro.model';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-livro-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>Livros</h2>
      <a routerLink="/livros/novo" class="btn btn-success">+ Novo Livro</a>
    </div>

    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead class="table-dark">
          <tr>
            <th>Título</th>
            <th>Valor</th>
            <th>Autores</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let livro of livros">
            <td>{{ livro.titulo }}</td>
            <td>{{ livro.valor | currency:'BRL' }}</td>
            <td>{{ authorsList(livro) }}</td>
            <td>
              <a routerLink="/livros/editar/{{livro.id}}" class="btn btn-sm btn-warning">Editar</a>
              <button class="btn btn-sm btn-danger ms-2" (click)="deletar(livro.id!)">Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class LivroListComponent implements OnInit {
  livros: Livro[] = [];

  constructor(private livroService: LivroService) {}

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.livroService.listar().subscribe(data => this.livros = data);
  }

  deletar(id: number) {
    if (confirm('Tem certeza?')) {
      this.livroService.deletar(id).subscribe(() => this.carregar());
    }
  }

  authorsList(livro: Livro): string {
    const nomes = livro.autores && livro.autores.length ? livro.autores.map(a => a.nome) : [];
    return nomes.length ? nomes.join(', ') : 'Sem autor';
  }
}