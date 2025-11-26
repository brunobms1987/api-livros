// Copie o autor-list e substitua "autor" por "assunto", Autor por Assunto, etc.
import { Component, OnInit } from '@angular/core';
import { AssuntoService } from '../../../services/assunto.service';
import { Assunto } from '../../../models/assunto.model';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-assunto-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>Assuntos</h2>
      <a routerLink="/assuntos/novo" class="btn btn-success">+ Novo Assunto</a>
    </div>

    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead class="table-dark">
          <tr>
            <th>Descrição</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let assunto of assuntos">
            <td>{{ assunto.descricao }}</td>
            <td>
              <a routerLink="/assuntos/editar/{{assunto.id}}" class="btn btn-sm btn-warning">Editar</a>
              <button class="btn btn-sm btn-danger ms-2" (click)="deletar(assunto.id!)">Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class AssuntoListComponent implements OnInit {
  assuntos: Assunto[] = [];

  constructor(private assuntoService: AssuntoService) {}

  ngOnInit() {
    this.assuntoService.listar().subscribe(data => this.assuntos = data);
  }

  deletar(id: number) {
    if (confirm('Tem certeza?')) {
      this.ngOnInit();
    }
  }
}