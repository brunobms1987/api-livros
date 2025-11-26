import { Component, OnInit } from '@angular/core';
import { AssuntoService } from '../../../services/assunto.service';
import { Assunto } from '../../../models/assunto.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-assunto-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="card">
      <div class="card-header">
        <h4>{{ assunto.id ? 'Editar' : 'Novo' }} Assunto</h4>
      </div>
      <div class="card-body">
        <form (ngSubmit)="salvar()">
          <div class="mb-3">
            <label class="form-label">Descrição *</label>
            <input class="form-control" [(ngModel)]="assunto.descricao" name="descricao" required>
          </div>

          <div class="mt-4">
            <button type="submit" class="btn btn-success me-2">Salvar</button>
            <a routerLink="/assuntos" class="btn btn-secondary">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
  `
})
export class AssuntoFormComponent implements OnInit {
  assunto: Assunto = { descricao: '' };

  constructor(
    private assuntoService: AssuntoService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.assuntoService.buscarPorId(+id).subscribe({
        next: (assuntoDoBanco) => {
          this.assunto = assuntoDoBanco;
        },
        error: () => alert('Erro ao carregar assunto')
      });
    } else {
      this.assunto = { descricao: '' };
    }
  }

  salvar(): void {
    
    this.assuntoService.salvar(this.assunto).subscribe({
      next: (res) => {
        alert('Salvo com sucesso!');
        this.router.navigate(['/assuntos']);
      },
      error: (err) => {
        alert('Erro ao salvar');
      }
    });
  }
}