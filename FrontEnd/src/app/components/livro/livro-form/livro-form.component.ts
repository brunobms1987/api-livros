import { Component, OnInit } from '@angular/core';
import { LivroService } from '../../../services/livro.service';
import { AutorService } from '../../../services/autor.service';
import { AssuntoService } from '../../../services/assunto.service';
import { Livro } from '../../../models/livro.model';
import { Autor } from '../../../models/autor.model';
import { Assunto } from '../../../models/assunto.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-livro-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './livro-form.component.html',
  styleUrls: ['./livro-form.component.css']
})
export class LivroFormComponent implements OnInit {
  livro: Livro = {
    titulo: '', editora: '', edicao: 1, anoPublicacao: '', valor: 0,
    autores: [], assuntos: []
  };
  autores: Autor[] = [];
  assuntos: Assunto[] = [];

  constructor(
    private livroService: LivroService,
    private autorService: AutorService,
    private assuntoService: AssuntoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.autorService.listar().subscribe(data => this.autores = data);
    this.assuntoService.listar().subscribe(data => this.assuntos = data);

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.livroService.buscarPorId(+id).subscribe(data => this.livro = data);
    }
  }

  salvar(): void {
    this.livroService.salvar(this.livro).subscribe({
      next: () => {
        alert('Salvo com sucesso!');
        this.router.navigate(['/livros']);
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao salvar: ' + err.message);
      }
    });
  }
}