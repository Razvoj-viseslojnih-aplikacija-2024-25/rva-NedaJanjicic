import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Film } from '../../../models/film';
import { FilmService } from '../../../services/film-service';
import { MatDialog } from '@angular/material/dialog';
import { FilmDialog } from '../../dialogs/film-dialog/film-dialog.component';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-film-component',
  standalone: true,
  imports: [MatTableModule, MatIconModule, MatToolbarModule],
  templateUrl: './film-componet.component.html',
  styleUrls: ['./film-componet.component.css'],
})
export class FilmComponent implements OnInit {
  displayedColumns = ['id', 'naziv', 'recenzija', 'trajanje', 'zanr', 'actions'];
  dataSource!: MatTableDataSource<Film>;

  constructor(private filmService: FilmService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.filmService.getAllFilms().subscribe({
      next: (data) => (this.dataSource = new MatTableDataSource<Film>(data)),
      error: (error) => console.log(error.message),
    });
  }

  public openDialog(flag: number, id?: number, naziv?: string, recenzija?: string, trajanje?: number, zanr?: string): void {
    const ref = this.dialog.open(FilmDialog, { data: { id, naziv, recenzija, trajanje, zanr } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) this.loadData();
    });
  }
}
