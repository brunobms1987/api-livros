import { Component, OnInit } from '@angular/core';
import { AutorService } from '../../../services/autor.service';
import { Autor } from '../../../models/autor.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-autor-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="card">
      <div class="card-header">
        <h4>{{ autor.id ? 'Editar' : 'Novo' }} Autor</h4>
      </div>
      <div class="card-body">
        <form (ngSubmit)="salvar()">
          <div class="mb-3">
            <label class="form-label">Nome *</label>
            <input class="form-control" [(ngModel)]="autor.nome" name="nome" required>
          </div>

          <div class="mt-4">
            <button type="submit" class="btn btn-success me-2">Salvar</button>
            <a routerLink="/autores" class="btn btn-secondary">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
  `
})
export class AutorFormComponent implements OnInit {
  autor: Autor = { nome: '' };

  constructor(
    private autorService: AutorService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.autorService.buscarPorId(+id).subscribe(data => this.autor = data);
    }
  }

  salvar(): void {
    this.autorService.salvar(this.autor).subscribe({
      next: () => {
        alert('Salvo com sucesso!');
        this.router.navigate(['/autores']);
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao salvar: ' + err.message);
      }
    });
  }
  
}