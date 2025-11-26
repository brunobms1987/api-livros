import { Component, OnInit } from '@angular/core';
import { AutorService } from '../../../services/autor.service';
import { Autor } from '../../../models/autor.model';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-autor-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>Autores</h2>
      <a routerLink="/autores/novo" class="btn btn-success">+ Novo Autor</a>
    </div>

    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead class="table-dark">
          <tr>
            <th>Nome</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let autor of autores">
            <td>{{ autor.nome }}</td>
            <td>
              <a routerLink="/autores/editar/{{autor.id}}" class="btn btn-sm btn-warning">Editar</a>
              <button class="btn btn-sm btn-danger ms-2" (click)="deletar(autor.id!)">Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class AutorListComponent implements OnInit {
  autores: Autor[] = [];

  constructor(private autorService: AutorService) {}

  ngOnInit() {
    this.autorService.listar().subscribe(data => this.autores = data);
  }

  deletar(id: number) {
    if (confirm('Tem certeza?')) {
      // Adicione o método deletar no AutorService se precisar
      // Por enquanto, só recarrega
      this.ngOnInit();
    }
  }
}